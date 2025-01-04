package com.example.mini_cockpit_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SharepointCaseDTO {

    private String title;
    private String insurance;
    private String caseType;
    private String zip;
    private LocalDateTime created;
    private String manufacturer;
    private String model;
    private String modelCode;
    private String customerName;
    private String customerMail;
    private int salesPersonId;
    private String salesPersonName;

}
