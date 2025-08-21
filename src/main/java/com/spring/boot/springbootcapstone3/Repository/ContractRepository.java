package com.spring.boot.springbootcapstone3.Repository;

import com.spring.boot.springbootcapstone3.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    boolean existsByServiceRequest_Id(Integer serviceRequestId);
    Contract findByServiceRequest_Id(Integer serviceRequestId);
    Contract findContractById(Integer id);
    @Query("""
           select c
           from Contract c
           left join fetch c.serviceRequest sr
           left join fetch sr.organization org
           left join fetch c.offer o
           left join fetch o.vendor v
           where c.id = :id
           """)
    Contract fetchGraphById(Integer id);

    @Query("SELECT c FROM Contract c JOIN c.offer o WHERE o.vendor.id = ?1")
    List<Contract> getContractsByStartDateAndEndDateBetweenAndVendorId(LocalDate startDate
            , LocalDate endDate
            , Integer vendorId);
}
