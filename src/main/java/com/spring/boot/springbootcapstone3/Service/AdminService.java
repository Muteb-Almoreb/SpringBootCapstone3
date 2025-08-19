package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Admin;
import com.spring.boot.springbootcapstone3.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;


    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    public void add(Admin in) {
        if (adminRepository.existsByEmail(in.getEmail())) {
            throw new ApiException("Email already exists");
        }
        adminRepository.save(in);
    }

    public void update(Integer id, Admin in) {
        Admin old = adminRepository.findAdminById(id);
        if (old == null) throw new ApiException("Admin not found");

        if (!old.getEmail().equals(in.getEmail()) && adminRepository.existsByEmail(in.getEmail())) {
            throw new ApiException("Email already exists");
        }

        old.setEmail(in.getEmail());
        old.setName(in.getName());
        adminRepository.save(old);
    }

    public void delete(Integer id) {
        Admin old = adminRepository.findAdminById(id);
        if (old == null) throw new ApiException("Admin not found");
        adminRepository.delete(old);
    }
}
