package com.digitaldoctor.digitaldoctor;

import com.digitaldoctor.digitaldoctor.entities.*;
import com.digitaldoctor.digitaldoctor.repositories.DoctorRepository;
import com.digitaldoctor.digitaldoctor.repositories.DrugRepository;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import com.digitaldoctor.digitaldoctor.repositories.PrescriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class DBLoader {
    private static final Logger log = LoggerFactory.getLogger(DBLoader.class);

    @Bean
    CommandLineRunner initDatabase(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            PrescriptionRepository prescriptionRepository,
            DrugRepository drugRepository
    ) {
        return args -> {
            initDoctor(doctorRepository);
            initPatient(patientRepository);
            initDrug(drugRepository);
            initPrescription(prescriptionRepository, patientRepository, doctorRepository, drugRepository);
        };
    }

    private void initPatient(PatientRepository repository) {
        insertIfNonExistent(repository, 1L, new Patient(
                1L,
                "Hubertus",
                "Maier",
                new Date(1955, 10, 01),
                new Address(null, "Theresienstraße", "95", "12803", "Waldhausen"),
                new Workplace(null, "ABC GmbH", "mail@abc.de", new Address(null, "Haldenweg", "7", "12805", "Waldhausen")),
                null,
                null,
                new InsuranceCard(null, "23345823", new Insurance(null, "Die Versicherung", new Address()))
        ));
    }

    private void initDoctor(DoctorRepository repository) {
        insertIfNonExistent(repository, 1L, new Doctor(
                1L,
                "Johannes",
                "Müller",
                new Address(null, "Sophienstraße", "41", "82375", "Hintertupfingen"),
                "Allgemeinmediziner"));
        insertIfNonExistent(repository, 2L, new Doctor(
                2L,
                "Peter",
                "Meier",
                new Address(null, "Gartenweg", "7", "32042", "Mühlheim"),
                "HNO"));
    }

    private void initDrug(DrugRepository repository) {
        insertIfNonExistent(repository, "AAA123", new Drug(
                "AAA123",
                "My Drug",
                new Manufacturer(null, null, "That Manufacturer", new Address()),
                "None",
                "Oral",
                null
        ));
    }

    private void initPrescription(
            PrescriptionRepository prescriptionRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            DrugRepository drugRepository) {
        Doctor doctor = doctorRepository.findById(1L).orElseThrow();
        Patient patient = patientRepository.findById(1L).orElseThrow();
        Drug drug = drugRepository.findById("AAA123").orElseThrow();
        insertIfNonExistent(prescriptionRepository, 1L, new Prescription(
                1L,
                patient,
                doctor,
                drug,
                "Jeden Morgen"
        ));
    }

    private <T, S> void insertIfNonExistent(JpaRepository<T, S> repository, S id, T object) {
        if (repository.findById(id).isEmpty())
            log.info("Preloading " + repository.save(object));
    }
}
