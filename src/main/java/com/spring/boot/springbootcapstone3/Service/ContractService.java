package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.DTO.*;
import com.spring.boot.springbootcapstone3.Model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Repository.ContractRepository;
import com.spring.boot.springbootcapstone3.Repository.OfferRepository;
import org.springframework.web.bind.annotation.PathVariable;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final OfferRepository offerRepository;

    public Contract getById(Integer id) {
        Contract c = contractRepository.findContractById(id);
        if (c == null) throw new ApiException("Contract not found");
        return c;
    }

    public Contract getByServiceRequestId(Integer srId) {
        Contract c = contractRepository.findByServiceRequest_Id(srId);
        if (c == null) throw new ApiException("No contract for this ServiceRequest");
        return c;
    }

    public Contract getByOfferId(Integer offerId) {
        Offer o = offerRepository.findOfferById(offerId);
        if (o == null) throw new ApiException("Offer not found");
        Contract c = o.getContract();
        if (c == null) throw new ApiException("No contract linked to this offer");
        return c;
    }

    public Contract updateBasicFields(Integer id, Double price, LocalDate startDate, LocalDate endDate) {
        Contract c = contractRepository.findContractById(id);
        if (c == null) throw new ApiException("Contract not found");

        if (price != null) {
            if (price < 0) throw new ApiException("Price cannot be negative");
            c.setPrice(price);
        }
        if (startDate != null) c.setStartDate(startDate);
        if (endDate != null)   c.setEndDate(endDate);

        if (c.getStartDate() != null && c.getEndDate() != null && c.getEndDate().isBefore(c.getStartDate())) {
            throw new ApiException("endDate must be after startDate");
        }

        return contractRepository.save(c);
    }

    public Contract getContractGraph(Integer id) {
        Contract c = contractRepository.fetchGraphById(id);
        if (c == null) throw new ApiException("Contract not found");
        return c;
    }


    public ContractPrintResponse buildPrintView(Integer id) {
        Contract c = getContractGraph(id);

        // contract
        ContractSummaryDTO contractDto = new ContractSummaryDTO(
                c.getPrice(),
                c.getStartDate(),
                c.getEndDate()

        );

        // serviceRequest + org
        ServiceRequest sr = c.getServiceRequest();
        Organization org = (sr != null) ? sr.getOrganization() : null;

        ServiceRequestSummaryDTO srDto = (sr == null) ? null :
                new ServiceRequestSummaryDTO(
                        sr.getTitle(),
                        sr.getLocation(),
                        sr.getLocationUrl()
                );

        OrganizationSummaryDTO orgDto = (org == null) ? null :
                new OrganizationSummaryDTO(
                        org.getName(),
                        org.getEmail(),
                        org.getPhone()
                );

        // offer + vendor
        Offer offer = c.getOffer();
        Vendor vendor = (offer != null) ? offer.getVendor() : null;

        OfferDTO offerDto = (offer == null) ? null :
                new OfferDTO(
                        offer.getTitle(),
                        offer.getDescription(),
                        offer.getPrice()
                );

        VendorSummaryDTO vendorDto = (vendor == null) ? null :
                new VendorSummaryDTO(
                        vendor.getName(),
                        vendor.getEmail(),
                        vendor.getPhone()
                );

        PartiesDTO parties = new PartiesDTO(orgDto, vendorDto);


        return new ContractPrintResponse(
                contractDto,
                parties,
                srDto,
                offerDto
        );
    }

    public List<Contract> getContractsBetweenDates(LocalDate startDate, LocalDate endDate, Integer vendorId){
        return contractRepository.giveMeContractsByStartDateAndEndDateBetweenAndVendorId(startDate, endDate, vendorId);
    }

    public List<Contract> getOverdueContracts(Integer vendorId){
        return contractRepository.giveMeOverdueContracts(vendorId);
    }

    public List<Contract> getAlmostExpiredContracts(Integer vendorId){
        return contractRepository.giveMeAlmostExpiredContracts(vendorId);
    }

    //  11. contract renewal, date must be after end date,
    //      it updates the contract payment status
    //      and creates a new start-end date that is equivalent to the number of days

    public void renewContract(Integer vendorId, Integer contractId){
        Contract contract = getById(contractId); // this will check for contract existance

        if (!contract.getOffer().getVendor().getId().equals(vendorId)){
            throw new ApiException("error, the contract is not owned by the vendor specified");
        }

        if (contract.getEndDate().isAfter(LocalDate.now())){
            throw new ApiException("Error, the contract did not expire yet!");
        }

        contract.setStatus("UNPAID"); // reset the payment status to allow another payment with the same price
        long daysBetween = ChronoUnit.DAYS.between(contract.getStartDate(), contract.getEndDate());

        contract.setStartDate(LocalDate.now().plusDays(1));
        contract.setEndDate(LocalDate.now().plusDays(1+daysBetween));

        contractRepository.save(contract);
    }

    // TODO:
    //  7. refund request
    //  8. sync invoice status from Moyasar API
    //  9. /contracts/summary
    //            This endpoint could provide a summary of contracts
    //            such as the total value of all active contracts
    //            or the number of contracts set to expire in the next month.
    //            It aggregates data, going beyond a simple list of records.

}
