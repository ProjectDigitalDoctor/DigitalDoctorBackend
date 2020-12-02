package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;

@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
    private Timestamp timestamp;
    private Duration duration;
    private String reason;
}
