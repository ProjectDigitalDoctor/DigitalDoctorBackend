package com.digitaldoctor.digitaldoctor.repositories;

import com.digitaldoctor.digitaldoctor.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Transactional
    Optional<Patient> findByUsername(String username);
}
