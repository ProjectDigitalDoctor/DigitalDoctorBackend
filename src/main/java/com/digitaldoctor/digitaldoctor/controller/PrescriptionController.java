package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.*;
import com.digitaldoctor.digitaldoctor.repositories.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

class PrescriptionNotFoundException extends RuntimeException {
    PrescriptionNotFoundException(Long id) {
        super("Could not find prescription with id " + id);
    }
}

class PrescriptionAlreadyRedeemedException extends RuntimeException {
    PrescriptionAlreadyRedeemedException(Long id) {
        super("Prescription with id " + id + " was already redeemed");
    }
}

class OfferNotFoundException extends RuntimeException {
    OfferNotFoundException(Long prescriptionID, Long offerID) {
        super("Could not find offer with id " + offerID + " for prescription with id " + prescriptionID);
    }
}

@RestController
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DrugRepository drugRepository;
    private final AuthPatientProvider authPatientProvider;

    @GetMapping("/prescription")
    List<Prescription> getPrescriptions() {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        return prescriptionRepository.findByPatientId(patient.getId());
    }

    @GetMapping("/prescription/{id}")
    Prescription getPrescription(@PathVariable Long id) {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        return prescriptionRepository.findByIdAndPatientId(id, patient.getId())
                .orElseThrow(() -> new PrescriptionNotFoundException(id));
    }

    @PostMapping("/prescription/{prescriptionId}/offer/{offerId}/order")
    void orderPrescription(@PathVariable Long prescriptionId, @PathVariable Long offerId) {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        Prescription prescription = prescriptionRepository.findByIdAndPatientId(prescriptionId, patient.getId())
                .orElseThrow(() -> new PrescriptionNotFoundException(prescriptionId));

        if (prescription.getRedeemed()) {
            throw new PrescriptionAlreadyRedeemedException(prescriptionId);
        }

        Offer offer = prescription.getDrug().getOffers().stream()
                .filter((Offer findOffer) -> findOffer.getId().equals(offerId)).findFirst()
                .orElseThrow(() -> new OfferNotFoundException(prescriptionId, offerId));

        prescription.setRedeemed(true);
        prescriptionRepository.save(prescription);
    }

    @GetMapping("/prescription/{prescriptionId}/redeem")
    void redeemPrescription(@PathVariable Long prescriptionId) {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        Prescription prescription = prescriptionRepository.findByIdAndPatientId(prescriptionId, patient.getId())
                .orElseThrow(() -> new PrescriptionNotFoundException(prescriptionId));

        if (prescription.getRedeemed()) {
            throw new PrescriptionAlreadyRedeemedException(prescriptionId);
        }

        prescription.setRedeemed(true);
        prescriptionRepository.save(prescription);
    }

    @AllArgsConstructor
    @ToString
    private static class RequestPrescription {
        public Long patientID;
        public Long doctorID;
        public String drugPZN;
        public String usage;
        public String dateOfIssue;
        public String validUntil;
        public boolean redeemed;
    }

    @PostMapping("/prescription")
    void createPrescription(@RequestBody RequestPrescription requestPrescription) {
        Patient patient = patientRepository.findById(requestPrescription.patientID).orElseThrow();
        Doctor doctor = doctorRepository.findById(requestPrescription.doctorID).orElseThrow();
        Drug drug = drugRepository.findById(requestPrescription.drugPZN).orElseThrow();

        Prescription prescription = new Prescription(null, patient, doctor, drug, requestPrescription.usage, Date.valueOf(requestPrescription.dateOfIssue), Date.valueOf(requestPrescription.validUntil), requestPrescription.redeemed);
        System.out.println("Created " + prescriptionRepository.saveAndFlush(prescription));
    }
}
