package com.spring.boot.springbootcapstone3.Controller;


import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.DTO.OfferDTO;
import com.spring.boot.springbootcapstone3.Model.Offer;
import com.spring.boot.springbootcapstone3.Service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {


        private final OfferService offerService;

        @GetMapping("/getAll")
        public ResponseEntity<?> getAll() {
            return ResponseEntity.ok(offerService.getAll());
        }

        @GetMapping("/getOfferBy/{id}")
        public ResponseEntity<?> getOfferBy(@PathVariable Integer id) {
            return ResponseEntity.ok(offerService.getById(id));
        }

        @GetMapping("/getByServiceRequest/{serviceRequestId}")
        public ResponseEntity<?> getByServiceRequest(@PathVariable Integer serviceRequestId) {
            return ResponseEntity.ok(offerService.getByServiceRequest(serviceRequestId));
        }

        @GetMapping("/getByVendor/{vendorId}")
        public ResponseEntity<?> getByVendor(@PathVariable Integer vendorId) {
            return ResponseEntity.ok(offerService.getByVendor(vendorId));
        }

        @PostMapping("/addOffer/{serviceRequestId}/{vendorId}")
        public ResponseEntity<?> addOffer(@PathVariable Integer serviceRequestId,
                                          @PathVariable Integer vendorId,
                                          @Valid @RequestBody OfferDTO dto) {
            offerService.add(serviceRequestId, vendorId, dto);
            return ResponseEntity.ok(new ApiResponse("Offer added successfully"));
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<?> update(@PathVariable Integer id,
                                        @Valid @RequestBody OfferDTO dto) {
            offerService.update(id, dto);
            return ResponseEntity.ok(new ApiResponse("Offer updated successfully"));
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<?> delete(@PathVariable Integer id) {
            offerService.delete(id);
            return ResponseEntity.ok(new ApiResponse("Offer deleted successfully"));
        }
}
