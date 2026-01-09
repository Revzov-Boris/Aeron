package com.example.AeroN.repositories;

import com.example.AeroN.enteties.AirEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirRepository extends JpaRepository<AirEntity, Integer> {
}
