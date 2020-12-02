package com.digitaldoctor.digitaldoctor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private Date birthdate;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @ManyToOne(cascade = CascadeType.ALL)
    private Workplace workplace;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Prescription> prescriptions;
    @OneToOne(cascade = CascadeType.ALL)
    private InsuranceCard insuranceCard;
}
