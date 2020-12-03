package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Appointment;
import com.digitaldoctor.digitaldoctor.entities.Doctor;
import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.repositories.AppointmentRepository;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

class AppointmentNotFoundException extends RuntimeException {
    AppointmentNotFoundException(Long id) {
        super("Could not find appointment with id " + id);
    }
}

@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;
    private final Long loggedInUserID = 1L;

    @GetMapping("/appointment")
    List<Appointment> getAppointments() {
        return appointmentRepository.findByPatientId(loggedInUserID);
    }

    @PostMapping("/appointment")
    Appointment createAppointment(@RequestBody Appointment newAppointment) {
        Patient overwritePatient = new Patient();
        overwritePatient.setId(newAppointment.getPatient().getId());
        newAppointment.setPatient(overwritePatient);
        return appointmentRepository.save(newAppointment);
    }

    @GetMapping("/appointment/{id}")
    Appointment getAppointment(@PathVariable Long id) {
        return appointmentRepository.findByIdAndPatientId(id, loggedInUserID)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @PutMapping("/appointment/{id}")
    Appointment updateAppointment(@RequestBody Appointment updatedAppointment, @PathVariable Long id) {
        return appointmentRepository.findByIdAndPatientId(id, loggedInUserID)
                .map(appointment -> {
                    appointment.setTimestamp(updatedAppointment.getTimestamp());
                    appointment.setDuration(updatedAppointment.getDuration());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @DeleteMapping("/appointment/{id}")
    void deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteByIdAndPatientId(id, loggedInUserID);
    }

    @PostMapping("/appointment/{id}/join")
    Object joinAppointment(@PathVariable Long id) {
        return null;
    }
}
