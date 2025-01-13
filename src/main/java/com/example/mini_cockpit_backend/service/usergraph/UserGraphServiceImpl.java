package com.example.mini_cockpit_backend.service.usergraph;

import com.example.mini_cockpit_backend.dto.graphs.GraphData;
import com.example.mini_cockpit_backend.dto.graphs.GraphEnabledDTO;
import com.example.mini_cockpit_backend.entity.Graph;
import com.example.mini_cockpit_backend.entity.User;
import com.example.mini_cockpit_backend.service.graph.GraphService;
import com.example.mini_cockpit_backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserGraphServiceImpl implements UserGraphService {

    private final UserService userService;
    private final GraphService graphService;

    @Override
    public void enableUserGraphs(String email, List<GraphEnabledDTO> graphs) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            User foundUser = user.get();

            for (GraphEnabledDTO graphDTO : graphs) {
                Graph graph = graphService.getById(graphDTO.getId());

                if (graphDTO.isEnabled() && !foundUser.getGraphs().contains(graph)) {
                    foundUser.getGraphs().add(graph);
                    graph.getUsers().add(foundUser);

                } else if (!graphDTO.isEnabled() && foundUser.getGraphs().contains(graph)) {
                    foundUser.getGraphs().remove(graph);
                    graph.getUsers().remove(foundUser);
                }
            }

            userService.save(foundUser);

        }
    }

    @Override
    public List<GraphEnabledDTO> getEnabledGraphs(String email) {
        List<Graph> graphs = graphService.getGraphs();
        Optional<User> user = userService.findByEmail(email);
        List<GraphEnabledDTO> userGraphs = new ArrayList<>();

        if (user.isPresent()) {
            User foundUser = user.get();

            for (Graph graph : graphs) {

                GraphEnabledDTO userGraph = GraphEnabledDTO.builder()
                        .isEnabled(foundUser.getGraphs().contains(graph))
                        .id(graph.getId())
                        .name(graph.getName())
                        .build();

                userGraphs.add(userGraph);

            }


        }

        return userGraphs;
    }


}
