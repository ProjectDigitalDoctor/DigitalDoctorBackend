package com.digitaldoctor.digitaldoctor.repositories;

import com.digitaldoctor.digitaldoctor.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Transactional
    List<Appointment> findByPatientId(Long patientId);
    @Transactional
    Optional<Appointment> findByIdAndPatientId(Long id, Long patientId);
    @Transactional
    void deleteByIdAndPatientId(Long id, Long patientId);
}
