package com.digitaldoctor.digitaldoctor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Drug drug;
    private String usageDescription;
    private Date dateOfIssue;
    private Date validUntil;
}
