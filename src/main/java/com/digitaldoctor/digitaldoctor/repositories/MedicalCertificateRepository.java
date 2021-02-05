package com.digitaldoctor.digitaldoctor.repositories;

import com.digitaldoctor.digitaldoctor.entities.MedicalCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long> {
    @Transactional
    List<MedicalCertificate> findByPatientId(Long patientId);
    @Transactional
    Optional<MedicalCertificate> findByIdAndPatientId(Long id, Long patientId);
}
