package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.dto.graphs.PostGraphDTO;
import com.example.mini_cockpit_backend.entity.Graph;
import com.example.mini_cockpit_backend.service.graph.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://matwn.dk")
@RequiredArgsConstructor
@RequestMapping("/api/mini/graph")
public class GraphController {

    private final GraphService graphService;

    @PostMapping("/metabase")
    public ResponseEntity<List<Graph>> postMetabaseGraphIfNotExist(@RequestBody List<PostGraphDTO> postGraphDTOs) {
        List<Graph> graphs = graphService.postIfNotExist(postGraphDTOs);
        return new ResponseEntity<>(graphs, HttpStatus.OK);
    }

    /*
    @GetMapping("/metabase")
    public ResponseEntity<List<Graph>> getGraphInfo() {
        return new ResponseEntity<>(graphService.getMetabaseGraphInfo(), HttpStatus.OK);
    }

    */


}
