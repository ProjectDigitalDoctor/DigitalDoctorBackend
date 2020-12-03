package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Drug;
import com.digitaldoctor.digitaldoctor.entities.MedicalCertificate;
import com.digitaldoctor.digitaldoctor.repositories.DrugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

class DrugNotFoundException extends RuntimeException {
    DrugNotFoundException(String pzn) {
        super("Could not find drug with pzn " + pzn);
    }
}

@RestController
@RequiredArgsConstructor
public class DrugController {
    private final DrugRepository drugRepository;

    @GetMapping("/drug/{pzn}")
    Drug getDrug(@PathVariable String pzn) {
        return drugRepository.findById(pzn).orElseThrow(() -> new DrugNotFoundException(pzn));
    }
}
