package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Drug;
import com.digitaldoctor.digitaldoctor.entities.MedicalCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DrugController {
    @GetMapping("/drug/{id}")
    Drug getDrug(@PathVariable Long id) {
        Drug drug = new Drug();
        drug.setName("Test Drug");
        return drug;
    }
}
