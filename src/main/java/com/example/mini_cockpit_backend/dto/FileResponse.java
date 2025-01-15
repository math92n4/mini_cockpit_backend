package com.example.mini_cockpit_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {

    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        messages.add(message);
    }
}
