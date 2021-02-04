package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

class PatientNotFoundException extends RuntimeException {
    PatientNotFoundException() {
        super("Could not find patient");
    }
}

@RestController
@RequiredArgsConstructor
public class PatientController {
    private final AuthPatientProvider authPatientProvider;
    private final PatientRepository patientRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/patient")
    Patient getLoggedInPatient() {
        return authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
    }

    @PostMapping("/patient")
    Patient createPatient(@RequestBody Patient newPatient) {
        newPatient.setPassword(bCryptPasswordEncoder.encode(newPatient.getPassword()));
        return patientRepository.save(newPatient);
    }

    @PutMapping("/patient")
    Patient updateLoggedInPatient(@RequestBody Patient updatedPatient) {
        return authPatientProvider.getLoggedInPatient()
                .map(patient -> {
                    patient.setFirstName(updatedPatient.getFirstName());
                    patient.setLastName(updatedPatient.getLastName());
                    patient.setBirthdate(updatedPatient.getBirthdate());
                    patient.setInsuranceCard(updatedPatient.getInsuranceCard());
                    patient.setWorkplace(updatedPatient.getWorkplace());
                    return patientRepository.save(patient);
                })
                .orElseGet(() -> patientRepository.save(updatedPatient));
    }
}
