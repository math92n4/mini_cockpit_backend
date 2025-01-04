package com.example.mini_cockpit_backend.repository;

import com.example.mini_cockpit_backend.entity.Brand;
import com.example.mini_cockpit_backend.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    Model findByModelCode(String modelCode);
    Model findByModelDescription(String modelDescription);
}
