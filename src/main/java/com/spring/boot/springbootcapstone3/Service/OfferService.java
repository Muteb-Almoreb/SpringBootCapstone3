package com.spring.boot.springbootcapstone3.Service;


import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Offer;
import com.spring.boot.springbootcapstone3.Model.Organization;
import com.spring.boot.springbootcapstone3.Model.ServiceRequest;
import com.spring.boot.springbootcapstone3.Repository.OfferRepository;
import com.spring.boot.springbootcapstone3.Repository.OrganizationRepository;
import com.spring.boot.springbootcapstone3.Repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {


    private final OfferRepository offerRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final OrganizationRepository organizationRepository;

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

    public List<Offer> getByProvider(Integer providerOrgId) {
        Organization org = organizationRepository.findOrganizationById(providerOrgId);
        if (org == null) throw new ApiException("Organization (provider) id not found");
        return offerRepository.findAllByProvider_Id(providerOrgId);
    }

    public void add(Integer serviceRequestId, Integer providerOrgId, Offer offer) {
        ServiceRequest sr = serviceRequestRepository.findServiceRequestById(serviceRequestId);
        if (sr == null) throw new ApiException("Service request id not found");

        Organization provider = organizationRepository.findOrganizationById(providerOrgId);
        if (provider == null) throw new ApiException("Organization (provider) id not found");

        offer.setServiceRequest(sr);
        offer.setProvider(provider);

        offerRepository.save(offer);
    }

    public void update(Integer id, Offer newOffer) {
        Offer old = offerRepository.findOfferById(id);
        if (old == null) throw new ApiException("Offer id not found");

        old.setTitle(newOffer.getTitle());
        old.setDescription(newOffer.getDescription());
        old.setPrice(newOffer.getPrice());
        // status مؤجّل
        offerRepository.save(old);
    }

    public void delete(Integer id) {
        Offer offer = offerRepository.findOfferById(id);
        if (offer == null) throw new ApiException("Offer id not found");
        offerRepository.delete(offer);
    }
}

