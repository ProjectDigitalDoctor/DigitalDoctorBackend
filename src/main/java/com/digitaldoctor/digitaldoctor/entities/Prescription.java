package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Drug drug;
    private String usageDescription;
}
