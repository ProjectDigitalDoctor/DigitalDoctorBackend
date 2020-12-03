package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.MedicalCertificate;
import com.digitaldoctor.digitaldoctor.entities.Prescription;
import com.digitaldoctor.digitaldoctor.repositories.MedicalCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

class MedicalCertificateNotFoundException extends RuntimeException {
    MedicalCertificateNotFoundException(Long id) {
        super("Could not find medical certificate with id " + id);
    }
}

@RestController
@RequiredArgsConstructor
public class MedicalCertificateController {
    private final MedicalCertificateRepository medicalCertificateRepository;
    private final Long loggedInUserID = 1L;

    @GetMapping("/medical-certificate")
    List<MedicalCertificate> getMedicalCertificates() {
        return medicalCertificateRepository.findByPatientId(loggedInUserID);
    }

    @GetMapping("/medical-certificate/{id}")
    MedicalCertificate getMedicalCertificate(@PathVariable Long id) {
        return medicalCertificateRepository.findByIdAndPatientId(id, loggedInUserID)
                .orElseThrow(() -> new MedicalCertificateNotFoundException(id));
    }
}
