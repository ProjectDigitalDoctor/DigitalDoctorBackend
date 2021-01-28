package com.digitaldoctor.digitaldoctor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
    private Timestamp timestamp;
    private Integer duration; // in minutes
    private String reason;
    private String videoRoomName;
}
