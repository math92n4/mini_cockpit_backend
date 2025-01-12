package com.example.mini_cockpit_backend.service.metabase;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MetabaseService {

    @Value("${metabase.secret.key}")
    private String METABASE_KEY;

    @Value("${metabase.url}")
    private String METABASE_URL;

    public String signEmbedUrl(int questionId) {
        System.out.println(questionId);
        Map<String, Object> payload = new HashMap<>();
        payload.put("resource", Map.of("question", questionId));
        payload.put("params", new HashMap<>());
        payload.put("exp", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)); // Token expires in 1 hour


        String token = Jwts.builder()
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, METABASE_KEY.getBytes())
                .compact();

        return METABASE_URL + "/embed/question/" + token;
    }
}
