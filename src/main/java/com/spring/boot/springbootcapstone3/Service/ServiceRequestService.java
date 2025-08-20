package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Organization;
import com.spring.boot.springbootcapstone3.Model.ServiceRequest;
import com.spring.boot.springbootcapstone3.Repository.OrganizationRepository;
import com.spring.boot.springbootcapstone3.Repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final OrganizationRepository organizationRepository;

    public List<ServiceRequest> getAll() {
        return serviceRequestRepository.findAll();
    }

    public ServiceRequest getById(Integer id) {
        ServiceRequest sr = serviceRequestRepository.findServiceRequestById(id);
        if (sr == null)
            throw new ApiException("Service request id not found");
        return sr;
    }

    public List<ServiceRequest> getByOrganization(Integer organizationId) {
        // يتأكد أن الأورقانيزيشن موجود (اختياري لكن أفضل)
        Organization org = organizationRepository.findOrganizationById(organizationId);
        if (org == null)
            throw new ApiException("Organization id not found");
        return serviceRequestRepository.findAllByOrganization_Id(organizationId);
    }




    public void add(Integer organizationId, ServiceRequest req) {
        Organization org = organizationRepository.findOrganizationById(organizationId);
        if (org == null) throw new ApiException("Organization id not found");

        req.setOrganization(org);
        if (req.getCreatedAt() == null) req.setCreatedAt(LocalDateTime.now());

        if (req.getStatus() == null || req.getStatus().isBlank()) {
            req.setStatus("OPEN");
        } else if (!req.getStatus().equalsIgnoreCase("OPEN") && !req.getStatus().equalsIgnoreCase("CLOSED")) {
            throw new ApiException("Status must be OPEN or CLOSED");
        }

        serviceRequestRepository.save(req);
    }

    public void update(Integer id, ServiceRequest newReq) {
        ServiceRequest old = serviceRequestRepository.findServiceRequestById(id);
        if (old == null) throw new ApiException("Service request id not found");

        old.setTitle(newReq.getTitle());
        old.setDescription(newReq.getDescription());
        old.setLocation(newReq.getLocation());
        old.setLocationUrl(newReq.getLocationUrl());

        serviceRequestRepository.save(old);
    }

    public void delete(Integer id) {
        ServiceRequest sr = serviceRequestRepository.findServiceRequestById(id);
        if (sr == null)
            throw new ApiException("Service request id not found");
        serviceRequestRepository.delete(sr);
    }




}
