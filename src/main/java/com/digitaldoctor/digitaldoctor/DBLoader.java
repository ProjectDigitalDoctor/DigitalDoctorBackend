package com.digitaldoctor.digitaldoctor;

import com.digitaldoctor.digitaldoctor.entities.*;
import com.digitaldoctor.digitaldoctor.repositories.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.sql.Timestamp;

@Configuration
@AllArgsConstructor
public class DBLoader {
    private static final Logger log = LoggerFactory.getLogger(DBLoader.class);

    @Bean
    CommandLineRunner initDatabase(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            PrescriptionRepository prescriptionRepository,
            DrugRepository drugRepository,
            MedicalCertificateRepository medicalCertificateRepository,
            AppointmentRepository appointmentRepository,
            ShopRepository shopRepository,
            OfferRepository offerRepository
    ) {
        return args -> {
            initDoctor(doctorRepository);
            initPatient(patientRepository);
            initDrug(drugRepository);
            initPrescription(prescriptionRepository, patientRepository, doctorRepository, drugRepository);
            initMedicalCertificate(medicalCertificateRepository, patientRepository, doctorRepository);
            initAppointment(appointmentRepository, patientRepository, doctorRepository);
            initShop(shopRepository);
            initOffers(offerRepository, drugRepository, shopRepository);
        };
    }

    private void initPatient(PatientRepository repository) {
        insertIfNonExistent(repository, 1L, new Patient(
                1L,
                "Hubertus",
                "Maier",
                "hubertus.maier",
                new BCryptPasswordEncoder().encode("password123"),
                Date.valueOf("1954-12-28"),
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
                "Dr. Johannes",
                "Müller",
                new Address(null, "Sophienstraße", "41", "82375", "Hintertupfingen"),
                "Allgemeinmediziner"));
        insertIfNonExistent(repository, 2L, new Doctor(
                2L,
                "Dr. Peter",
                "Meier",
                new Address(null, "Gartenweg", "7", "32042", "Mühlheim"),
                "HNO"));
    }

    private void initDrug(DrugRepository repository) {
        insertIfNonExistent(repository, "AAA123", new Drug(
                "AAA123",
                "Schmerzmittel",
                new Manufacturer(null, null, "Medicine GmbH", new Address()),
                "None",
                "Oral",
                null
        ));
        insertIfNonExistent(repository, "BBB345", new Drug(
                "BBB345",
                "Erkältungsmittel",
                new Manufacturer(null, null, "Medicine GmbH", new Address()),
                "None",
                "Oral",
                null
        ));
    }

    private void initShop(ShopRepository repository) {
        insertIfNonExistent(repository, 1L, new Shop(
                1L,
                "Online Apotheke Pelikan",
                new Address(null, "Marktstraße", "27", "21343", "Berlin"),
                "info@apotheke-pelikan.de",
                null
        ));
        insertIfNonExistent(repository, 2L, new Shop(
                2L,
                "Online Apotheke Schwan",
                new Address(null, "Hermannstraße", "82A", "93234", "Hamburg"),
                "info@apotheke-schwan.de",
                null
        ));
    }

    private void initOffers(OfferRepository repository, DrugRepository drugRepository, ShopRepository shopRepository) {
        Drug drug1 = drugRepository.findById("AAA123").orElseThrow();
        Drug drug2 = drugRepository.findById("BBB345").orElseThrow();
        Shop shop1 = shopRepository.findById(1L).orElseThrow();
        Shop shop2 = shopRepository.findById(2L).orElseThrow();
        insertIfNonExistent(repository, 1L, new Offer(
                1L,
                drug1,
                shop1,
                123.45f
        ));
        insertIfNonExistent(repository, 2L, new Offer(
                2L,
                drug1,
                shop2,
                98.76f
        ));
        insertIfNonExistent(repository, 3L, new Offer(
                3L,
                drug2,
                shop1,
                13.4f
        ));
        insertIfNonExistent(repository, 4L, new Offer(
                4L,
                drug2,
                shop2,
                9.7f
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
        Drug drug2 = drugRepository.findById("BBB345").orElseThrow();
        insertIfNonExistent(prescriptionRepository, 1L, new Prescription(
                1L,
                patient,
                doctor,
                drug,
                "Jeden Morgen",
                Date.valueOf("2020-02-05"),
                Date.valueOf("2021-03-15"),
                false
        ));
        insertIfNonExistent(prescriptionRepository, 2L, new Prescription(
                2L,
                patient,
                doctor,
                drug,
                "Jeden Abend",
                Date.valueOf("2020-10-15"),
                Date.valueOf("2021-01-20"),
                true
        ));
        insertIfNonExistent(prescriptionRepository, 3L, new Prescription(
                3L,
                patient,
                doctor,
                drug2,
                "Jeden Morgen",
                Date.valueOf("2021-02-11"),
                Date.valueOf("2021-03-11"),
                false
        ));
    }

    private void initMedicalCertificate(
            MedicalCertificateRepository medicalCertificateRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository
    ) {
        Doctor doctor = doctorRepository.findById(1L).orElseThrow();
        Patient patient = patientRepository.findById(1L).orElseThrow();
        insertIfNonExistent(medicalCertificateRepository, 1L, new MedicalCertificate(
                1L,
                patient,
                doctor,
                "Urlaubsentzug",
                Date.valueOf("2021-01-01"),
                Date.valueOf("2021-01-15")
        ));
        insertIfNonExistent(medicalCertificateRepository, 2L, new MedicalCertificate(
                2L,
                patient,
                doctor,
                "Arbeitsunfähig aufgrund von Rückenschmerzen",
                Date.valueOf("2021-02-05"),
                Date.valueOf("2021-02-10")
        ));
        insertIfNonExistent(medicalCertificateRepository, 3L, new MedicalCertificate(
                3L,
                patient,
                doctor,
                "Arbeitsunfähig aufgrund einer starken Erkältung",
                Date.valueOf("2021-02-11"),
                Date.valueOf("2021-02-20")
        ));
    }

    private void initAppointment(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository
    ) {
        Doctor doctor = doctorRepository.findById(1L).orElseThrow();
        Patient patient = patientRepository.findById(1L).orElseThrow();
        insertIfNonExistent(appointmentRepository, 1L, new Appointment(
                1L,
                patient,
                doctor,
                Timestamp.valueOf("2021-01-11 10:15:00"),
                15,
                "Routine Untersuchung",
                null
        ));
        insertIfNonExistent(appointmentRepository, 2L, new Appointment(
                2L,
                patient,
                doctor,
                Timestamp.valueOf("2021-02-05 08:00:00"),
                20,
                "Rückenschmerzen",
                null
        ));
        insertIfNonExistent(appointmentRepository, 3L, new Appointment(
                3L,
                patient,
                doctor,
                Timestamp.valueOf("2021-02-23 08:00:00"),
                10,
                "Zusätzliche Kontrolle",
                null
        ));
    }

    private <T, S> void insertIfNonExistent(JpaRepository<T, S> repository, S id, T object) {
        if (repository.findById(id).isEmpty())
            log.info("Preloading " + repository.save(object));
    }
}
