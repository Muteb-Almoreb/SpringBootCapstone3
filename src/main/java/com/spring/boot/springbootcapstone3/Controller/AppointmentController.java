package com.spring.boot.springbootcapstone3.Controller;

import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.Model.Appointment;
import com.spring.boot.springbootcapstone3.Service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/add")
    public ResponseEntity<?> addAppointment(@Valid @RequestBody Appointment appointment){
        appointmentService.addAppointment(appointment);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Appointment added successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAppointments(){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAppointments());

    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@PathVariable Integer appointmentId, @Valid @RequestBody Appointment appointment){
        appointmentService.updateAppointment(appointmentId, appointment);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Appointment updated successfully"));

    }

    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer appointmentId){
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Appointment deleted successfully"));

    }
}
