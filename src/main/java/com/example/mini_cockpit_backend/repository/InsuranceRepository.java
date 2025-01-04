package com.example.mini_cockpit_backend.repository;

import com.example.mini_cockpit_backend.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

    Insurance findByName(String name);
}
