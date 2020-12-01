package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.entities.Doctor;
import com.digitaldoctor.digitaldoctor.entities.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    @GetMapping("/doctor/{id}")
    Doctor getDoctorByID(@PathVariable Long id) {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Johanned");
        doctor.setLastName("Müller");
        return doctor;
    }

    @GetMapping("/doctor/search")
    List<Doctor> searchDoctors(@RequestBody Object searchParameters) {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(getDoctorByID(0L));
        return doctorList;
    }
}
