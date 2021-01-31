package com.digitaldoctor.digitaldoctor.repositories;

import com.digitaldoctor.digitaldoctor.entities.Appointment;
import com.digitaldoctor.digitaldoctor.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
