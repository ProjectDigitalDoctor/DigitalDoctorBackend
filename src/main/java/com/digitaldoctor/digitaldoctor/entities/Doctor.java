package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    @OneToOne
    private Address address;
    private String profession; //TODO: Create enum? Table for this?

}
