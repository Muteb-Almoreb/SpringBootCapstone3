package com.spring.boot.springbootcapstone3.Controller;

import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.Model.Organization;
import com.spring.boot.springbootcapstone3.Service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {


    private final OrganizationService organizationService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @GetMapping("/getOrganizationBy/{id}")
    public ResponseEntity<?> getOrganizationBy(@PathVariable Integer id) {
        return ResponseEntity.ok(organizationService.getById(id));
    }

    @PostMapping("/addOrganization")
    public ResponseEntity<?> addOrganization(@Valid @RequestBody Organization organization) {

        organizationService.add(organization);
        return ResponseEntity.status(200).body(new ApiResponse("Organization added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Organization organization){
        organizationService.update(id, organization);
        return ResponseEntity.status(200).body(new ApiResponse("Organization updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        organizationService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Organization deleted successfully"));
    }
}
