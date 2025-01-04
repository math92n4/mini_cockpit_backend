package com.example.mini_cockpit_backend.repository;

import com.example.mini_cockpit_backend.entity.SalesPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesPersonRepository extends JpaRepository<SalesPerson, Integer> {

}
