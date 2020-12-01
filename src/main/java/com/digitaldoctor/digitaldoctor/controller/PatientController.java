package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PatientController {
    private static PatientRepository patientRepository;

    @GetMapping("/patient")
    Patient getLoggedInPatient() {
        Patient patient = new Patient();
        patient.setFirstName("Hubertus");
        patient.setLastName("Maier");
        return patient;
    }

    @PostMapping("/patient")
    Patient createPatient(@RequestBody Patient newPatient) {
        return newPatient;
    }

    @PutMapping("/patient")
    void updateLoggedInPatient(@RequestBody Patient updatedPatient) {
    }
}
