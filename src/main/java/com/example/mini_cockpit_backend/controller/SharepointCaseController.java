package com.example.mini_cockpit_backend.controller;

import com.example.mini_cockpit_backend.service.sharepoint.SharepointCaseService;
import com.example.mini_cockpit_backend.service.sharepoint.SharepointCaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mini/api/sp/case")
public class SharepointCaseController {

    private final SharepointCaseService sharepointCaseService;

    @PostMapping("")
    public ResponseEntity<String> postCaseData() {
        try {
            sharepointCaseService.saveSharepointData();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
