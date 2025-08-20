//package com.spring.boot.springbootcapstone3.Service;
//
//import com.spring.boot.springbootcapstone3.API.ApiException;
//import com.spring.boot.springbootcapstone3.DTO.ContractDTOIn;
//import com.spring.boot.springbootcapstone3.Model.Contract;
//import com.spring.boot.springbootcapstone3.Model.Organization;
//import com.spring.boot.springbootcapstone3.Model.ServiceRequest;
//import com.spring.boot.springbootcapstone3.Model.Vendor;
//import com.spring.boot.springbootcapstone3.Repository.ContractRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ContractService { // Created by Abdullah Alwael
//    private final ContractRepository contractRepository;
//    private final ServiceRequestService serviceRequestService;
//    private final OrganizationService organizationService;
//    private final VendorService vendorService;
//
//    public void addContract(ContractDTOIn contractDTOIn){
//        ServiceRequest serviceRequest = serviceRequestService.getById(contractDTOIn.getServiceRequestId());
//        Organization organization = organizationService.getById(contractDTOIn.getOrganizationId());
//        Vendor vendor = vendorService.getVendor(contractDTOIn.getVendorId());
//        // invoice is created later?
//
//        if (serviceRequest == null){
//            throw new ApiException("Error, serviceRequest not found");
//        }
//
//        if (organization == null){
//            throw new ApiException("Error, organization not found");
//        }
//
//        if (vendor == null){
//            throw new ApiException("Error, vendor not found");
//        }
//
//        Contract contract = new Contract(null, contractDTOIn.getTotalPrice(), contractDTOIn.getStartDate()
//                , contractDTOIn.getEndDate(), contractDTOIn.getCareScope(), "INITIAL_STATUS"
//              update status to mach system flow
//                ,contractDTOIn.getContractLocationName(), contractDTOIn.getContractLocationUrl()
//                , contractDTOIn.getContractItemsJson(), null, serviceRequest, organization, vendor);
//        contractRepository.save(contract);
//    }
//
//    public List<Contract> getContracts(){
//        return contractRepository.findAll();
//    }
//
//    public Contract getContract(Integer contractId){
//        return contractRepository.findContractById(contractId);
//    }
//
//    public void updateContract(ContractDTOIn contractDTOIn){
//        Contract oldContract = getContract(contractDTOIn.getServiceRequestId());
//
//        if (oldContract == null){
//            throw new ApiException("Error, contract not found");
//        }
//
//
//        oldContract.setContractLocationName(contractDTOIn.getContractLocationName());
//        oldContract.setContractLocationUrl(contractDTOIn.getContractLocationUrl());
//        oldContract.setContractItemsJson(contractDTOIn.getContractItemsJson());
//        oldContract.setCareScope(contractDTOIn.getCareScope());
//        oldContract.setStartDate(contractDTOIn.getStartDate());
//        oldContract.setEndDate(contractDTOIn.getEndDate());
//       oldContract.setStatus(contractDTOIn.getStatus());
//        oldContract.setTotalPrice(contractDTOIn.getTotalPrice());
//
//        contractRepository.save(oldContract);
//    }
//
//    public void deleteContract(Integer contractId){
//        Contract oldContract = getContract(contractId);
//
//        if (oldContract == null){
//            throw new ApiException("Error, contract not found");
//        }
//
//        oldContract.setOrganization(null);
//        oldContract.setVendor(null);
//        contractRepository.delete(oldContract);
//    }
//}
