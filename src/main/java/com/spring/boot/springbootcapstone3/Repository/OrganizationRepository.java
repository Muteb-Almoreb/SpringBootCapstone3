package com.spring.boot.springbootcapstone3.Repository;

import com.spring.boot.springbootcapstone3.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository <Organization , Integer> {
    Organization findOrganizationById(Integer id);
}
