package com.digitaldoctor.digitaldoctor.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
}
