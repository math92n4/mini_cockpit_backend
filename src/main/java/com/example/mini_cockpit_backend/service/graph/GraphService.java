package com.example.mini_cockpit_backend.service.graph;

import com.example.mini_cockpit_backend.dto.graphs.GraphData;
import com.example.mini_cockpit_backend.dto.graphs.PostGraphDTO;
import com.example.mini_cockpit_backend.dto.graphs.SalesPrModel;
import com.example.mini_cockpit_backend.dto.graphs.SalesPrMonth;
import com.example.mini_cockpit_backend.entity.Graph;

import java.util.List;

public interface GraphService {

    List<SalesPrModel> getSalesPrModel();

    List<SalesPrMonth> getSalesPrMonth();

    GraphData getGraphData();

    void postGraph(PostGraphDTO postGraphDTO);

    /*
    List<Graph> getMetabaseGraphInfo();
     */
    Graph getById(int id);

    List<Graph> postIfNotExist(List<PostGraphDTO> postGraphDTOs);

    public void save(Graph graph);

    List<Graph> getGraphs();
}
