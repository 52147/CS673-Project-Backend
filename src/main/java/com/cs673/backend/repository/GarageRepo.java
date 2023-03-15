package com.cs673.backend.repository;

import com.cs673.backend.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepo extends JpaRepository<Garage, Integer> {
    Garage findFirstByOrderByIdAsc();
}
