package com.example.mini_cockpit_backend.service.usergraph;

import com.example.mini_cockpit_backend.dto.graphs.GraphEnabledDTO;
import com.example.mini_cockpit_backend.entity.Graph;

import java.util.List;
import java.util.Set;

public interface UserGraphService {

    void enableUserGraphs(String email, List<GraphEnabledDTO> graphs);

    List<GraphEnabledDTO> getEnabledGraphs(String email);

}
