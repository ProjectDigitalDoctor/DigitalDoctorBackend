package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private Date birthdate;
    @OneToOne
    private Address address;
    @ManyToOne
    private Workplace workplace;
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "patient")
    private List<Prescription> prescriptions;
    @OneToOne
    private InsuranceCard insuranceCard;
}
