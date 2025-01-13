package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.dto.graphs.EmbedUrlResponse;
import com.example.mini_cockpit_backend.dto.graphs.PostGraphDTO;
import com.example.mini_cockpit_backend.entity.Graph;
import com.example.mini_cockpit_backend.entity.User;
import com.example.mini_cockpit_backend.service.graph.GraphService;
import com.example.mini_cockpit_backend.service.metabase.MetabaseService;
import com.example.mini_cockpit_backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "https://matwn.dk")
@RequiredArgsConstructor
public class MetabaseController {

    private final MetabaseService metabaseService;
    private final GraphService graphService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MetabaseController.class);

    @PostMapping("/api/mini/metabase")
    public ResponseEntity<List<EmbedUrlResponse>> getGraph(@RequestBody List<PostGraphDTO> postGraphDTOS) {

        graphService.postIfNotExist(postGraphDTOS);

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            String email = authentication.getName();

            List<EmbedUrlResponse> urlList = new ArrayList<>();
            Optional<User> loggedInUser = userService.findByEmail(email);

            if (loggedInUser.isPresent()) {
                Set<Graph> userGraphs = loggedInUser.get().getGraphs();

                for (Graph graph : userGraphs) {
                    String embedUrl = metabaseService.signEmbedUrl(graph.getId());
                    EmbedUrlResponse embedUrlResponse = new EmbedUrlResponse(embedUrl);
                    urlList.add(embedUrlResponse);
                }
                return new ResponseEntity<>(urlList, HttpStatus.OK);
            }



            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
