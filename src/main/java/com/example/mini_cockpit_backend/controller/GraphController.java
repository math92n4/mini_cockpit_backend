package com.example.mini_cockpit_backend.controller;
import com.example.mini_cockpit_backend.dto.graphs.GraphEnabledDTO;
import com.example.mini_cockpit_backend.service.usergraph.UserGraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://matwn.dk")
@RequiredArgsConstructor
@RequestMapping("/api/mini/graph")
public class GraphController {

    private final UserGraphService userGraphService;

    @PostMapping("/enable")
    public ResponseEntity<String> enableGraphs(@RequestBody List<GraphEnabledDTO> graphs) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();
        userGraphService.enableUserGraphs(email, graphs);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<GraphEnabledDTO>> getUserGraphs() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();
        List<GraphEnabledDTO> userGraphs = userGraphService.getEnabledGraphs(email);
        return new ResponseEntity<>(userGraphs, HttpStatus.OK);
    }

}
