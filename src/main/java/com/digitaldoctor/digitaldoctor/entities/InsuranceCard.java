package com.digitaldoctor.digitaldoctor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String insuranceNumber;
    @ManyToOne(cascade = CascadeType.ALL)
    private Insurance insurance;
}
