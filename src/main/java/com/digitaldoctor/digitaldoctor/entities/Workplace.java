package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Workplace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String mailAddress;

    @ManyToOne
    private Address address;
}
