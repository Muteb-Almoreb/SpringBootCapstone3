package com.spring.boot.springbootcapstone3.Service;


import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.DTO.OfferDTO;
import com.spring.boot.springbootcapstone3.Model.*;
import com.spring.boot.springbootcapstone3.Repository.ContractRepository;
import com.spring.boot.springbootcapstone3.Repository.OfferRepository;
import com.spring.boot.springbootcapstone3.Repository.ServiceRequestRepository;
import com.spring.boot.springbootcapstone3.Repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final VendorRepository vendorRepository;
    private final ContractRepository contractRepository;

    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    public Offer getById(Integer id) {
        Offer offer = offerRepository.findOfferById(id);
        if (offer == null) throw new ApiException("Offer id not found");
        return offer;
    }

    public List<Offer> getByServiceRequest(Integer serviceRequestId) {
        ServiceRequest sr = serviceRequestRepository.findServiceRequestById(serviceRequestId);
        if (sr == null) throw new ApiException("Service request id not found");
        return offerRepository.findAllByServiceRequest_Id(serviceRequestId);
    }

    public List<Offer> getByVendor(Integer vendorId) {
        Vendor vendor = vendorRepository.findVendorById(vendorId);
        if (vendor == null) throw new ApiException("Vendor id not found");
        return offerRepository.findAllByVendor_Id(vendorId);
    }
    //ادد عادية بدون DTO
//    public void add(Integer serviceRequestId, Integer vendorId, Offer offer) {
//        ServiceRequest sr = serviceRequestRepository.findServiceRequestById(serviceRequestId);
//        if (sr == null)
//            throw new ApiException("Service request id not found");
//
//        Vendor vendor = vendorRepository.findVendorById(vendorId);
//        if (vendor == null)
//            throw new ApiException("Vendor id not found");
//
//        offer.setServiceRequest(sr);
//        offer.setVendor(vendor);
//
//        offerRepository.save(offer);
//    }


    public void add(Integer serviceRequestId, Integer vendorId, OfferDTO dto) {
        ServiceRequest sr = serviceRequestRepository.findServiceRequestById(serviceRequestId);
        if (sr == null)
            throw new ApiException("Service request id not found");

        Vendor vendor = vendorRepository.findVendorById(vendorId);
        if (vendor == null)
            throw new ApiException("Vendor id not found");

        if (offerRepository.existsByServiceRequest_IdAndVendor_Id(serviceRequestId, vendorId)) {
            throw new ApiException("Vendor already submitted an offer for this service request");
        }

        Offer offer = new Offer();
        offer.setServiceRequest(sr);
        offer.setVendor(vendor);
        offer.setPrice(dto.getPrice());
        offer.setTitle(dto.getTitle());
        offer.setDescription(dto.getDescription());

        offer.setStatus("PENDING");

        offerRepository.save(offer);
    }


    //update with DTO
    public void update(Integer id, OfferDTO dto) {
        Offer old = offerRepository.findOfferById(id);
        if (old == null) throw new ApiException("Offer id not found");


        if (!"PENDING".equalsIgnoreCase(old.getStatus())) {
             throw new ApiException("Cannot update non-PENDING offer");
         }

        old.setPrice(dto.getPrice());
        old.setTitle(dto.getTitle());
        old.setDescription(dto.getDescription());
        offerRepository.save(old);
    }




    //update Without DTO

//    public void update(Integer id, Offer newOffer) {
//        Offer old = offerRepository.findOfferById(id);
//        if (old == null) throw new ApiException("Offer id not found");
//
//        old.setTitle(newOffer.getTitle());
//        old.setDescription(newOffer.getDescription());
//        old.setPrice(newOffer.getPrice());
//        // status مؤجّل
//        offerRepository.save(old);
//    }


    public void delete(Integer id) {
        Offer offer = offerRepository.findOfferById(id);
        if (offer == null)
            throw new ApiException("Offer id not found");

        if ("ACCEPTED".equalsIgnoreCase(offer.getStatus()))
            throw new ApiException("Cannot delete an accepted offer");

        offerRepository.delete(offer);
    }

    public Contract acceptById(Integer offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ApiException("Offer not found"));

        ServiceRequest sr = offer.getServiceRequest();
        if (sr == null) throw new ApiException("Offer not linked to a ServiceRequest");

        if (!"PENDING".equalsIgnoreCase(offer.getStatus())) {
            throw new ApiException("Only PENDING offers can be accepted");
        }
        if (offerRepository.countByServiceRequest_IdAndStatus(sr.getId(), "APPROVED") > 0) {
            throw new ApiException("Another offer is already approved for this ServiceRequest");
        }
        if (sr.getContract() != null || offer.getContract() != null) {
            throw new ApiException("A contract already exists for this request/offer");
        }

        offer.setStatus("APPROVED");
        offerRepository.save(offer);

        for (Offer o : offerRepository.findAllByServiceRequest_Id(sr.getId())) {
            if (!o.getId().equals(offer.getId()) && !"REJECTED".equalsIgnoreCase(o.getStatus())) {
                o.setStatus("REJECTED");
                offerRepository.save(o);
            }
        }

        Contract c = new Contract();
        c.setServiceRequest(sr);
        c.setPrice(offer.getPrice() != null ? offer.getPrice() : 0.0);

        LocalDate now = LocalDate.now();
        c.setStartDate(now);
        c.setEndDate(now.plusMonths(12));

        offer.setContract(c);
        offerRepository.save(offer);

        return offer.getContract();
    }


    public Contract createContractIfApproved(Integer offerId,
                                             LocalDate startDate,
                                             LocalDate endDate) {

        Offer offer = offerRepository.findOfferById(offerId);
        if (offer == null) {
            throw new ApiException("Offer not found");
        }

        if (!"APPROVED".equalsIgnoreCase(offer.getStatus())) {
            throw new ApiException("Offer must be APPROVED before creating a contract");
        }

        ServiceRequest sr = offer.getServiceRequest();
        if (sr == null) {
            throw new ApiException("Offer is not linked to a ServiceRequest");
        }

        if (contractRepository.existsByServiceRequest_Id(sr.getId())) {
            throw new ApiException("A contract already exists for this ServiceRequest");
        }
        if (offer.getContract() != null) {
            throw new ApiException("This offer already has a contract");
        }

        LocalDate start = (startDate != null) ? startDate : LocalDate.now();
        LocalDate end   = (endDate   != null) ? endDate   : start.plusMonths(12);

        Contract c = new Contract();
        c.setPrice(offer.getPrice() != null ? offer.getPrice() : 0.0);
        c.setStartDate(start);
        c.setEndDate(end);
        c.setServiceRequest(sr);

        Contract saved = contractRepository.save(c);

        offer.setContract(saved);
        offerRepository.save(offer);

        return saved;
    }
}


