package com.digitaldoctor.digitaldoctor.repositories;

import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Transactional
    List<Prescription> findByPatientId(Long patientId);
    @Transactional
    Optional<Prescription> findByIdAndPatientId(Long id, Long patientId);
}
