package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Offer;
import com.digitaldoctor.digitaldoctor.entities.Prescription;
import com.digitaldoctor.digitaldoctor.repositories.OfferRepository;
import com.digitaldoctor.digitaldoctor.repositories.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/prescription/{prescriptionId}/offer/{offerId}/order")
    void orderPrescription(@PathVariable Long prescriptionId, @PathVariable Long offerId) {
        Prescription prescription = prescriptionRepository.findByIdAndPatientId(prescriptionId, loggedInUserID)
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
        Prescription prescription = prescriptionRepository.findByIdAndPatientId(prescriptionId, loggedInUserID)
                .orElseThrow(() -> new PrescriptionNotFoundException(prescriptionId));

        if (prescription.getRedeemed()) {
            throw new PrescriptionAlreadyRedeemedException(prescriptionId);
        }

        prescription.setRedeemed(true);
        prescriptionRepository.save(prescription);
    }
}
