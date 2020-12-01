package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.MedicalCertificate;
import com.digitaldoctor.digitaldoctor.entities.Prescription;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedicalCertificateController {
    @GetMapping("/medical-certificate")
    List<MedicalCertificate> getMedicalCertificates() {
        List<MedicalCertificate> medicalCertificates = new ArrayList<>();
        medicalCertificates.add(getMedicalCertificate(0L));
        return medicalCertificates;
    }

    @GetMapping("/medical-certificate/{id}")
    MedicalCertificate getMedicalCertificate(@PathVariable Long id) {
        MedicalCertificate medicalCertificate = new MedicalCertificate();
        medicalCertificate.setReason("Test Medical Certificate");
        return medicalCertificate;
    }
}
