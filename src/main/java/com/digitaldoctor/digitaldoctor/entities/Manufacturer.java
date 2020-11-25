package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "manufacturer")
    private List<Drug> drugs;
    private String name;
    @OneToOne
    private Address address;
}
