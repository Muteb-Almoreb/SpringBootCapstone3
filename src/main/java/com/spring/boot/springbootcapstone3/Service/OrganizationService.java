package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Organization;
import com.spring.boot.springbootcapstone3.Repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Organization getById(Integer id) {
        Organization org = organizationRepository.findOrganizationById(id);
        if (org == null) throw new ApiException("Organization id not found");
        return org;
    }

    public void add(Organization organization) {
        organizationRepository.save(organization);
    }

    public void update(Integer id, Organization newOrg) {
        Organization oldOrganization = organizationRepository.findOrganizationById(id);
        if (oldOrganization == null)
            throw new ApiException("Organization id not found");

        oldOrganization.setName(newOrg.getName());
        oldOrganization.setEmail(newOrg.getEmail());
        oldOrganization.setPhone(newOrg.getPhone());
        oldOrganization.setCommercialRegister(newOrg.getCommercialRegister());

        organizationRepository.save(oldOrganization);
    }

    public void delete(Integer id) {
        Organization org = organizationRepository.findOrganizationById(id);
        if (org == null)
            throw new ApiException("Organization id not found");
        organizationRepository.delete(org);
    }


}
