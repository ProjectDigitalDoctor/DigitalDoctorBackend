package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Appointment;
import com.digitaldoctor.digitaldoctor.entities.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {
    @GetMapping("/appointment")
    List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(getAppointment(0L));
        return appointments;
    }

    @PostMapping("/appointment")
    Appointment createAppointment(@RequestBody Appointment newAppointment) {
        return newAppointment;
    }

    @GetMapping("/appointment/{id}")
    Appointment getAppointment(@PathVariable Long id) {
        Appointment appointment = new Appointment();
        appointment.setReason("Test Appointment");
        return appointment;
    }

    @PutMapping("/appointment/{id}")
    void updateAppointment(@RequestBody Appointment updadetAppointment, @PathVariable Long id) {
    }

    @DeleteMapping("/appointment/{id}")
    void deleteAppointment(@PathVariable Long id) {
    }

    @PostMapping("/appointment/{id}/join")
    Object joinAppointment(@PathVariable Long id) {
        return null;
    }
}
