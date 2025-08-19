package com.spring.boot.springbootcapstone3.Repository;

import com.spring.boot.springbootcapstone3.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository <Offer , Integer> {

    Offer findOfferById(Integer id);
    List<Offer> findAllByServiceRequest_Id(Integer serviceRequestId);

    List<Offer> findAllByProvider_Id(Integer providerOrgId);
}
