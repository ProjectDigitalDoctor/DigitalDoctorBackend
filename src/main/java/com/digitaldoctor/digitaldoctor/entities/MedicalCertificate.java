package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class MedicalCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
    private String reason;
    private Date validUntil;
}
