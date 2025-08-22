package com.spring.boot.springbootcapstone3.Repository;

import com.spring.boot.springbootcapstone3.Model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Vendor findVendorById(Integer id);

    @Query("SELECT v, COUNT(o.id) AS acceptedOfferCount " +
            "FROM Vendor v " +
            "JOIN v.offers o " +
            "WHERE o.status = 'ACCEPTED' " +
            "GROUP BY v.name " +
            "ORDER BY acceptedOfferCount DESC " +
            "LIMIT 5")
    List<Vendor> giveMeTopFiveVendors();
}
