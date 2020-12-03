package com.digitaldoctor.digitaldoctor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Drug {
    @Id
    private String pzn;

    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    private Manufacturer manufacturer;
    private String sideEffects;
    private String usage; //TODO: Enum oder Tabelle?
    @OneToMany(mappedBy = "drug")
    @ToString.Exclude
    private List<Offer> offers;
}
