package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class InsuranceCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String insuranceNumber;
    @ManyToOne
    private Insurance insurance;
}
