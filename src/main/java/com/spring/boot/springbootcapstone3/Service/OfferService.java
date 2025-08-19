package com.spring.boot.springbootcapstone3.Service;


import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.DTO.OfferDTO;
import com.spring.boot.springbootcapstone3.Model.Offer;
import com.spring.boot.springbootcapstone3.Model.Organization;
import com.spring.boot.springbootcapstone3.Model.ServiceRequest;
import com.spring.boot.springbootcapstone3.Model.Vendor;
import com.spring.boot.springbootcapstone3.Repository.OfferRepository;
import com.spring.boot.springbootcapstone3.Repository.OrganizationRepository;
import com.spring.boot.springbootcapstone3.Repository.ServiceRequestRepository;
import com.spring.boot.springbootcapstone3.Repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final VendorRepository vendorRepository;

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
}

