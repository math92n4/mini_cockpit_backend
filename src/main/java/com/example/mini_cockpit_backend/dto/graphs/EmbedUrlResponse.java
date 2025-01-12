package com.example.mini_cockpit_backend.dto.graphs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmbedUrlResponse {

    private String url;

}
