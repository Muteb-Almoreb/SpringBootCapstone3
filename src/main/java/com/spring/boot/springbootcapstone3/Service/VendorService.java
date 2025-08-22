package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Vendor;
import com.spring.boot.springbootcapstone3.Repository.AdminRepository;
import com.spring.boot.springbootcapstone3.Repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private static final String PENDING   = "PENDING";
    private static final String APPROVED  = "APPROVED";
    private static final String REJECTED  = "REJECTED";
    private static final String SUSPENDED = "SUSPENDED";

    private final VendorRepository vendorRepository;
    private final AdminRepository adminRepository;

    public List<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    public Vendor getVendor(Integer vendorId){
        return vendorRepository.findVendorById(vendorId);
    }

    public void addVendor(Vendor in) {
        if (vendorRepository.existsByEmail(in.getEmail()))
            throw new ApiException("Email already exists");
        if (vendorRepository.existsByPhone(in.getPhone()))
            throw new ApiException("Phone already exists");

        in.setApprovalStatus("PENDING");
        vendorRepository.save(in);
    }

    public void update(Integer id, Vendor in) {
        Vendor old = ensureVendor(id);

        if (!old.getEmail().equals(in.getEmail()) && vendorRepository.existsByEmail(in.getEmail()))
            throw new ApiException("Email already exists");
        if (!old.getPhone().equals(in.getPhone()) && vendorRepository.existsByPhone(in.getPhone()))
            throw new ApiException("Phone already exists");

        old.setName(in.getName());
        old.setEmail(in.getEmail());
        old.setPhone(in.getPhone());
        vendorRepository.save(old);
    }

    public void delete(Integer id) {
        Vendor old = ensureVendor(id);
        vendorRepository.delete(old);
    }

    public void approve(Integer vendorId, Integer adminId) {
        ensureAdmin(adminId);
        Vendor v = ensureVendor(vendorId);

        String st = v.getApprovalStatus();
        if (st == null) st = PENDING;

        if (PENDING.equals(st)) {
            v.setApprovalStatus(APPROVED);
            vendorRepository.save(v);
            return;
        }
        if (SUSPENDED.equals(st)) {
            throw new ApiException("Use reinstate to move SUSPENDED → APPROVED");
        }
        if (APPROVED.equals(st)) {
            throw new ApiException("Vendor is already APPROVED");
        }
        if (REJECTED.equals(st)) {
            throw new ApiException("Cannot approve a REJECTED vendor");
        }
    }

    public void reject(Integer vendorId, Integer adminId) {
        ensureAdmin(adminId);
        Vendor v = ensureVendor(vendorId);

        String st = v.getApprovalStatus();
        if (st == null) st = PENDING;

        if (PENDING.equals(st)) {
            v.setApprovalStatus(REJECTED);
            vendorRepository.save(v);
            return;
        }
        throw new ApiException("Only PENDING vendors can be REJECTED");
    }

    public void suspend(Integer vendorId, Integer adminId) {
        ensureAdmin(adminId);
        Vendor v = ensureVendor(vendorId);

        String st = v.getApprovalStatus();
        if (APPROVED.equals(st)) {
            v.setApprovalStatus(SUSPENDED);
            vendorRepository.save(v);
            return;
        }
        throw new ApiException("Only APPROVED vendors can be SUSPENDED");
    }

    public void reinstate(Integer vendorId, Integer adminId) {
        ensureAdmin(adminId);
        Vendor v = ensureVendor(vendorId);

        String st = v.getApprovalStatus();
        if (SUSPENDED.equals(st)) {
            v.setApprovalStatus(APPROVED);
            vendorRepository.save(v);
            return;
        }
        throw new ApiException("Only SUSPENDED vendors can be REINSTATED");
    }

    private void ensureAdmin(Integer adminId) {
        if (adminRepository.findAdminById(adminId) == null)
            throw new ApiException("Admin not found");
    }

    private Vendor ensureVendor(Integer vendorId) {
        Vendor v = vendorRepository.findVendorById(vendorId);
        if (v == null) throw new ApiException("Vendor not found");
        return v;
    }

    public List<Vendor> getTopVendors(){
        return vendorRepository.giveMeTopFiveVendors();
    }
}
