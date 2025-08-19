package com.spring.boot.springbootcapstone3.Controller;


import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.Model.ServiceRequest;
import com.spring.boot.springbootcapstone3.Service.ServiceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class ServiceRequestController {


    private final ServiceRequestService serviceRequestService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(serviceRequestService.getAll());
    }

    @GetMapping("/getServiceRequestById/{id}")
    public ResponseEntity<?> getServiceRequestById(@PathVariable Integer id) {
        return ResponseEntity.ok(serviceRequestService.getById(id));
    }

    @GetMapping("/getByOrganizationId/{organizationId}")
    public ResponseEntity<?> getByOrganization(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(serviceRequestService.getByOrganization(organizationId));
    }

    @PostMapping("/addServiceRequest/{organizationId}")
    public ResponseEntity<?> addServiceRequest(@PathVariable Integer organizationId, @Valid @RequestBody ServiceRequest request) {
        serviceRequestService.add(organizationId, request);
        return ResponseEntity.status(200).body(new ApiResponse("Service request added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody ServiceRequest request) {
        serviceRequestService.update(id, request);
        return ResponseEntity.status(200).body(new ApiResponse("Service request updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        serviceRequestService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Service request deleted successfully"));
    }
}
