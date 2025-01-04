package com.example.mini_cockpit_backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IvsrDTO {

    private String productionNumber;
    private String modelCode;
    private String modelDescription;
    private String brand;
    private String colorCode;
    private String optionsString;
    private LocalDate actualProductionDate;
    private String customerLast;
    private LocalDate purchaseAgreementDate;
    private LocalDate retailCountingDate;
    private int salesPersonNumber;
    private LocalDate agreementDate;
    private String plannedHandoverWeek;
    private String expectedDeliveryWeek;

    private String customerName;
    private String customerMail;
    private String zip;

    private String salesPerson;

    private boolean sharepointData;
    private boolean ivsrData;
}
