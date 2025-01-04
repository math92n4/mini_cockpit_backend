package com.example.mini_cockpit_backend.service.model;

import com.example.mini_cockpit_backend.dto.graphs.SalesPrModel;
import com.example.mini_cockpit_backend.entity.Brand;
import com.example.mini_cockpit_backend.entity.Model;

import java.util.List;

public interface ModelService {

    Model save(Model model);

    Model find(String modelCode, String modelDesc);

}
