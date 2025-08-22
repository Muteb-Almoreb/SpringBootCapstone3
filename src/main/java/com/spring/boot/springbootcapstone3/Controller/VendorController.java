package com.spring.boot.springbootcapstone3.Controller;



import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.Model.Vendor;
import com.spring.boot.springbootcapstone3.Service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService service;


    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody Vendor body) {

        service.addVendor(body);
        return ResponseEntity.ok(new ApiResponse("Vendor created"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @Valid @RequestBody Vendor body) {
        service.update(id, body);
        return ResponseEntity.ok(new ApiResponse("Vendor updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse("Vendor deleted"));
    }


    @PutMapping("/{id}/approve/admin/{adminId}")
    public ResponseEntity<?> approve(@PathVariable Integer id, @PathVariable Integer adminId) {
        service.approve(id, adminId);
        return ResponseEntity.ok(new ApiResponse("Vendor approved"));
    }

    @PutMapping("/{id}/reject/admin/{adminId}")
    public ResponseEntity<?> reject(@PathVariable Integer id, @PathVariable Integer adminId) {
        service.reject(id, adminId);
        return ResponseEntity.ok(new ApiResponse("Vendor rejected"));
    }

    @PutMapping("/{id}/suspend/admin/{adminId}")
    public ResponseEntity<?> suspend(@PathVariable Integer id, @PathVariable Integer adminId) {
        service.suspend(id, adminId);
        return ResponseEntity.ok(new ApiResponse("Vendor suspended"));
    }

    @PutMapping("/{id}/reinstate/admin/{adminId}")
    public ResponseEntity<?> reinstate(@PathVariable Integer id, @PathVariable Integer adminId) {
        service.reinstate(id, adminId);
        return ResponseEntity.ok(new ApiResponse("Vendor reinstated"));
    }


    @GetMapping("/top-five")
    public ResponseEntity<?> getTopVendors(){
        return ResponseEntity.ok(service.getTopVendors());
    }
}
