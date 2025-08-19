package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Appointment;
import com.spring.boot.springbootcapstone3.Repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public void addAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointments(){
        return appointmentRepository.findAll();
    }

    public Appointment getAppointment(Integer appointmentId){
        return appointmentRepository.findAppointmentById(appointmentId);
    }

    public void updateAppointment(Integer appointmentId, Appointment appointment){
        Appointment oldAppointment = getAppointment(appointmentId);

        if (oldAppointment == null){
            throw new ApiException("Error, appointment not found");
        }

        oldAppointment.setRequestId(appointment.getRequestId());
        oldAppointment.setVendorId(appointment.getVendorId());
        oldAppointment.setScheduledAt(appointment.getScheduledAt());
        oldAppointment.setStatus(appointment.getStatus());
        oldAppointment.setNotes(appointment.getNotes());

        appointmentRepository.save(oldAppointment);
    }

    public void deleteAppointment(Integer appointmentId){
        Appointment oldAppointment = getAppointment(appointmentId);

        if (oldAppointment == null){
            throw new ApiException("Error, appointment not found");
        }

        appointmentRepository.delete(oldAppointment);
    }
}
