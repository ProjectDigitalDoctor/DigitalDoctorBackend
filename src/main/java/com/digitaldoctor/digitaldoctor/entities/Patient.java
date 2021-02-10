package com.digitaldoctor.digitaldoctor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @Column(unique = true)
    private String username;
    private String password;
    private Date birthdate;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @ManyToOne(cascade = CascadeType.ALL)
    private Workplace workplace;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    @ToString.Exclude
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    @ToString.Exclude
    private List<Prescription> prescriptions;
    @OneToOne(cascade = CascadeType.ALL)
    private InsuranceCard insuranceCard;
}
