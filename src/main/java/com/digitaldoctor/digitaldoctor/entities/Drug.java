package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Drug {
    @Id
    private String pzn;

    private String name;
    @ManyToOne
    private Manufacturer manufacturer;
    private String sideEffects;
    private String usage; //TODO: Enum oder Tabelle?
    @OneToMany(mappedBy = "drug")
    private List<Offer> offers;
}
