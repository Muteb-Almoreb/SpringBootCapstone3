package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Contract;
import com.spring.boot.springbootcapstone3.Repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService { // Created by Abdullah Alwael
    private final ContractRepository contractRepository;

    public void addContract(Contract contract){
        contractRepository.save(contract);
    }

    public List<Contract> getContracts(){
        return contractRepository.findAll();
    }

    public Contract getContract(Integer contractId){
        return contractRepository.findContractById(contractId);
    }

    public void updateContract(Integer contractId, Contract contract){
        Contract oldContract = getContract(contractId);

        if (oldContract == null){
            throw new ApiException("Error, contract not found");
        }

        oldContract.setContractLocationName(contract.getContractLocationName());
        oldContract.setContractItemsJson(contract.getContractItemsJson());
        oldContract.setCareScope(contract.getCareScope());
        oldContract.setStartDate(contract.getStartDate());
        oldContract.setEndDate(contract.getEndDate());
        oldContract.setStatus(contract.getStatus());
        oldContract.setRequestId(contract.getRequestId());
        oldContract.setTotalPrice(contract.getTotalPrice());

        oldContract.setOrganizationId(contract.getOrganizationId());
        oldContract.setVendorId(contract.getVendorId());

        contractRepository.save(oldContract);
    }

    public void deleteContract(Integer contractId){
        Contract oldContract = getContract(contractId);

        if (oldContract == null){
            throw new ApiException("Error, contract not found");
        }

        contractRepository.delete(oldContract);
    }
}
