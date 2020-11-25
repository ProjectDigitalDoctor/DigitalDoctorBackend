package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    @OneToOne
    private Address address;
    private String mailAddress;
    @OneToMany(mappedBy = "shop")
    private List<Offer> offers;
}
