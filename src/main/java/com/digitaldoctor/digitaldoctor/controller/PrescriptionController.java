package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Appointment;
import com.digitaldoctor.digitaldoctor.entities.Prescription;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrescriptionController {
    @GetMapping("/prescription")
    List<Prescription> getPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(getPrescription(0L));
        return prescriptions;
    }

    @GetMapping("/prescription/{id}")
    Prescription getPrescription(@PathVariable Long id) {
        Prescription prescription = new Prescription();
        prescription.setUsageDescription("Test Prescription");
        return prescription;
    }

    @GetMapping("/prescription/{id}/offer")
    Object getOffersForPrescription(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/prescription/{prescriptionId}/offer/{offerId}/order")
    Object orderPrescription(@PathVariable Long prescriptionId, @PathVariable Long offerId) {
        return null;
    }
}
