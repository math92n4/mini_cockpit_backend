package com.example.mini_cockpit_backend.repository;

import com.example.mini_cockpit_backend.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Brand findByName(String name);
}
