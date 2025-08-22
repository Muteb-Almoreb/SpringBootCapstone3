package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.DTO.*;
import com.spring.boot.springbootcapstone3.Model.*;
import lombok.*;
import org.springframework.stereotype.Service;
import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Repository.ContractRepository;
import com.spring.boot.springbootcapstone3.Repository.OfferRepository;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


import com.adobe.pdfservices.operation.PDFServices;
import com.adobe.pdfservices.operation.PDFServicesMediaType;
import com.adobe.pdfservices.operation.PDFServicesResponse;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.auth.ServicePrincipalCredentials;
import com.adobe.pdfservices.operation.exception.SDKException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.io.Asset;
import com.adobe.pdfservices.operation.io.StreamAsset;
import com.adobe.pdfservices.operation.pdfjobs.jobs.DocumentMergeJob;
import com.adobe.pdfservices.operation.pdfjobs.params.documentmerge.DocumentMergeParams;
import com.adobe.pdfservices.operation.pdfjobs.params.documentmerge.OutputFormat;
import com.adobe.pdfservices.operation.pdfjobs.result.DocumentMergeResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;



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



    @Value("97be5b0a848d4c4c928fc95e91d8b821")
    private String adobeClientId;

    @Value("p8e-BFjJw-bei7bjxop9aHuBXbFL-Sgoff-U")
    private String adobeClientSecret;

    /**
     * Generates a PDF for the given contract and returns a File ready to be attached in email.
     * - Uses your existing buildPrintView(id) (no Map usage)
     * - Loads template from classpath: src/main/resources/templates/contract-template.docx
     * - Creates a temp file like: /var/folders/.../contract-<id>-XXXXXX.pdf (macOS temp dir)
     *
     * @param id contract id
     * @return File pointing to the generated PDF (caller may delete it after sending email)
     */
    public File generateContractPdfFile(Integer id) {
        // 1) Build your data model
        ContractPrintResponse model = buildPrintView(id);

        try {
            // 2) Serialize DTOs -> JSON (no Map<String,Object>)
            ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonStr = om.writeValueAsString(model);
            JSONObject payload = new JSONObject(jsonStr);

            // 3) Credentials
            if (adobeClientId == null || adobeClientId.isBlank()
                    || adobeClientSecret == null || adobeClientSecret.isBlank()) {
                throw new ApiException("Missing Adobe PDF Services credentials in application.properties");
            }
            Credentials creds = new ServicePrincipalCredentials(adobeClientId, adobeClientSecret);
            PDFServices pdfServices = new PDFServices(creds);

            // 4) Load Word template from classpath
            ClassPathResource templateRes = new ClassPathResource("templates/contract-template.docx");
            if (!templateRes.exists()) {
                throw new ApiException("Template not found: templates/contract-template.docx");
            }

            Asset templateAsset;
            try (InputStream templateStream = templateRes.getInputStream()) {
                templateAsset = pdfServices.upload(templateStream, PDFServicesMediaType.DOCX.getMediaType());
            }

            // 5) Merge to PDF
            DocumentMergeParams params = DocumentMergeParams.documentMergeParamsBuilder()
                    .withJsonDataForMerge(payload)
                    .withOutputFormat(OutputFormat.PDF)
                    .build();

            DocumentMergeJob job = new DocumentMergeJob(templateAsset, params);
            String location = pdfServices.submit(job);
            PDFServicesResponse<DocumentMergeResult> resp =
                    pdfServices.getJobResult(location, DocumentMergeResult.class);

            Asset resultAsset = resp.getResult().getAsset();
            StreamAsset streamAsset = pdfServices.getContent(resultAsset);

            // 6) Read bytes
            byte[] pdfBytes;
            try (InputStream in = streamAsset.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                in.transferTo(out);
                pdfBytes = out.toByteArray();
            }

            // 7) Write to a temp file and return it
            Path tmp = Files.createTempFile("contract-" + id + "-", ".pdf");
            Files.write(tmp, pdfBytes);

            // Optional: auto-cleanup on JVM exit (you can remove if you prefer manual delete)
            tmp.toFile().deleteOnExit();

            return tmp.toFile();

        } catch (ServiceApiException | ServiceUsageException | SDKException e) {
            throw new ApiException("Adobe service error: " + e.getMessage());
        } catch (Exception e) {
            throw new ApiException("Failed to generate contract PDF: " + e.getMessage());
        }
    }



}
