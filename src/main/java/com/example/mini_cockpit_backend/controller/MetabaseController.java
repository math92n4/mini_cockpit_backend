package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.dto.graphs.EmbedUrlResponse;
import com.example.mini_cockpit_backend.dto.graphs.PostGraphDTO;
import com.example.mini_cockpit_backend.service.graph.GraphService;
import com.example.mini_cockpit_backend.service.metabase.MetabaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://matwn.dk")
@RequiredArgsConstructor
public class MetabaseController {

    private final MetabaseService metabaseService;
    private final GraphService graphService;
    private static final Logger logger = LoggerFactory.getLogger(MetabaseController.class);

    @PostMapping("/api/mini/metabase")
    public ResponseEntity<List<EmbedUrlResponse>> getGraph(@RequestBody List<PostGraphDTO> postGraphDTOS) {

        graphService.postIfNotExist(postGraphDTOS);

        try {
            List<EmbedUrlResponse> urlList = new ArrayList<>();

            for (PostGraphDTO postGraphDTO : postGraphDTOS) {
                String embedUrl = metabaseService.signEmbedUrl(postGraphDTO.getId());
                EmbedUrlResponse embedUrlResponse = new EmbedUrlResponse(embedUrl);
                urlList.add(embedUrlResponse);
            }

            return new ResponseEntity<>(urlList, HttpStatus.OK);

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
