package com.digitaldoctor.digitaldoctor.repositories;

import com.digitaldoctor.digitaldoctor.entities.MedicalCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long> {
    List<MedicalCertificate> findByPatientId(Long patientId);
    Optional<MedicalCertificate> findByIdAndPatientId(Long id, Long patientId);
}
