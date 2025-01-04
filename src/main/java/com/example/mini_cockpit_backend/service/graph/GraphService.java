package com.example.mini_cockpit_backend.service.graph;

import com.example.mini_cockpit_backend.dto.graphs.GraphData;
import com.example.mini_cockpit_backend.dto.graphs.SalesPrModel;
import com.example.mini_cockpit_backend.dto.graphs.SalesPrMonth;

import java.util.List;

public interface GraphService {

    List<SalesPrModel> getSalesPrModel();

    List<SalesPrMonth> getSalesPrMonth();

    GraphData getGraphData();
}
