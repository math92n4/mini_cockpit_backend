package com.example.mini_cockpit_backend.repository;

import com.example.mini_cockpit_backend.entity.CaseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseTypeRepository extends JpaRepository<CaseType, Integer> {

    CaseType findByName(String name);
}
