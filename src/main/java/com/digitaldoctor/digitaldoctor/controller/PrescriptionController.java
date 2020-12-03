package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Prescription;
import com.digitaldoctor.digitaldoctor.repositories.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

class PrescriptionNotFoundException extends RuntimeException {
    PrescriptionNotFoundException(Long id) {
        super("Could not find prescription with id " + id);
    }
}

@RestController
@RequiredArgsConstructor
public class PrescriptionController {
    private final PrescriptionRepository prescriptionRepository;
    private final Long loggedInUserID = 1L;

    @GetMapping("/prescription")
    List<Prescription> getPrescriptions() {
        return prescriptionRepository.findByPatientId(loggedInUserID);
    }

    @GetMapping("/prescription/{id}")
    Prescription getPrescription(@PathVariable Long id) {
        return prescriptionRepository.findByIdAndPatientId(id, loggedInUserID)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));
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
