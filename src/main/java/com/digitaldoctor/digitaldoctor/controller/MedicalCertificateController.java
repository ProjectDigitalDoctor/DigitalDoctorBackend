package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Doctor;
import com.digitaldoctor.digitaldoctor.entities.MedicalCertificate;
import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.entities.Prescription;
import com.digitaldoctor.digitaldoctor.repositories.DoctorRepository;
import com.digitaldoctor.digitaldoctor.repositories.MedicalCertificateRepository;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
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

    @AllArgsConstructor
    @ToString
    private static class RequestMedicalCertificate {
        public Long patientID;
        public Long doctorID;
        public String reason;
        public String validUntil;
    }

    @PostMapping("/medical-certificate")
    void createMedicalCertificate(@RequestBody RequestMedicalCertificate requestMedicalCertificate) {
        Patient patient = patientRepository.findById(requestMedicalCertificate.patientID).orElseThrow();
        Doctor doctor = doctorRepository.findById(requestMedicalCertificate.doctorID).orElseThrow();

        MedicalCertificate medicalCertificate = new MedicalCertificate(null, patient, doctor, requestMedicalCertificate.reason, Date.valueOf(requestMedicalCertificate.validUntil));
        System.out.println("Created " + medicalCertificateRepository.saveAndFlush(medicalCertificate));
    }
}
