package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.dto.graphs.EmbedUrlResponse;
import com.example.mini_cockpit_backend.service.metabase.MetabaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://matwn.dk")
@RequiredArgsConstructor
public class MetabaseController {

    private final MetabaseService metabaseService;
    private static final Logger logger = LoggerFactory.getLogger(MetabaseController.class);

    @GetMapping("/api/mini/metabase/{questionId}")
    public ResponseEntity<EmbedUrlResponse> getGraph(@PathVariable int questionId) {
        String embedUrl = metabaseService.signEmbedUrl(questionId);
        EmbedUrlResponse embedUrlResponse = new EmbedUrlResponse(embedUrl);
        return new ResponseEntity<>(embedUrlResponse, HttpStatus.OK);
    }

    @PostMapping("/metabase/api/card")
    public ResponseEntity<String> interceptMetabaseRequest(@RequestBody String requestBody) {
        System.out.println("Intercepted POST request to /metabase/api/card");
        System.out.println("Request Body: " + requestBody);
        logger.info(requestBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
