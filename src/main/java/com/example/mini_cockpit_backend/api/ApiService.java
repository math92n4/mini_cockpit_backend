package com.example.mini_cockpit_backend.api;

import com.example.mini_cockpit_backend.api.dto.IvsrDTO;
import com.example.mini_cockpit_backend.entity.Ivsr;
import com.example.mini_cockpit_backend.service.isvr.IvsrServiceImpl;
import com.example.mini_cockpit_backend.service.sharepoint.IvsrSharepointService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;

        public ResponseEntity<List<IvsrDTO>> sendIvsrRequest() {
            String url = "http://localhost:8081/sp/api/ivsr";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            headers.set("Content-Type", "application/json");

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            try {
                ResponseEntity<List<IvsrDTO>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<List<IvsrDTO>>() {}
                );


                return response;
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    }
