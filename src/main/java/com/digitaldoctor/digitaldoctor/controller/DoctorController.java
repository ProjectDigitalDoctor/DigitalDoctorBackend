package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Doctor;
import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorRepository doctorRepository;

    @GetMapping("/doctor/{id}")
    Doctor getDoctorByID(@PathVariable Long id) {
        return doctorRepository.findById(id).orElseThrow();
    }

    @GetMapping("/doctor/search")
    List<Doctor> searchDoctors(@RequestBody Object searchParameters) {
        return doctorRepository.findAll();
    }
}
