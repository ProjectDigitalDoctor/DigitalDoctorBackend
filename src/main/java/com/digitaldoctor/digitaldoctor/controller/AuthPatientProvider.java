package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthPatientProvider {
    private final PatientRepository patientRepository;

    public Optional<Patient> getLoggedInPatient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return patientRepository.findByUsername(auth.getName());
    }
}
