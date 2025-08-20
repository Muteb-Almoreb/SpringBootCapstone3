package com.spring.boot.springbootcapstone3.Repository;

import com.spring.boot.springbootcapstone3.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
// Created by Abdullah Alwael
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Contract findContractById(Integer id);
    boolean existsByServiceRequestId(Integer serviceRequestId);

}
