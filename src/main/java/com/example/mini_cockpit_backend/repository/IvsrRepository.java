package com.example.mini_cockpit_backend.repository;

import com.example.mini_cockpit_backend.dto.graphs.SalesPrModel;
import com.example.mini_cockpit_backend.entity.Ivsr;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IvsrRepository extends JpaRepository<Ivsr, String> {

    Ivsr getIvsrByProductionNumber(String productionNumber);

}
