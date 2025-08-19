package com.spring.boot.springbootcapstone3.Repository;

import com.spring.boot.springbootcapstone3.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Created by Abdullah Alwael
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findInvoiceById(Integer id);
}
