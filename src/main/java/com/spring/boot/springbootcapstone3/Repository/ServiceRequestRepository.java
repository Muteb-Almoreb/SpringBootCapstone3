package com.spring.boot.springbootcapstone3.Repository;

import com.spring.boot.springbootcapstone3.Model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository <ServiceRequest , Integer> {

    ServiceRequest findServiceRequestById(Integer id);
    List<ServiceRequest> findAllByOrganization_Id(Integer organizationId);

}
