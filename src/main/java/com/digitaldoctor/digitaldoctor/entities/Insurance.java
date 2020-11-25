package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    @OneToOne
    private Address address;
}
