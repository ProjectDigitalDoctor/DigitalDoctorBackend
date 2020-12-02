package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

class PatientNotFoundException extends RuntimeException {
    PatientNotFoundException(Long id) {
        super("Could not find patient with id " + id);
    }
}

@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientRepository patientRepository;
    private final Long loggedInUserID = 1L;

    @GetMapping("/patient")
    Patient getLoggedInPatient() {
        return patientRepository.findById(loggedInUserID)
                .orElseThrow(() -> new PatientNotFoundException(loggedInUserID));
    }

    @PostMapping("/patient")
    Patient createPatient(@RequestBody Patient newPatient) {
        return patientRepository.save(newPatient);
    }

    @PutMapping("/patient")
    Patient updateLoggedInPatient(@RequestBody Patient updatedPatient) {
        return patientRepository.findById(loggedInUserID)
                .map(patient -> {
                    patient.setFirstName(updatedPatient.getFirstName());
                    patient.setLastName(updatedPatient.getLastName());
                    patient.setBirthdate(updatedPatient.getBirthdate());
                    patient.setInsuranceCard(updatedPatient.getInsuranceCard());
                    patient.setWorkplace(updatedPatient.getWorkplace());
                    return patientRepository.save(patient);
                })
                .orElseGet(() -> {
                    return patientRepository.save(updatedPatient);
                });
    }
}
