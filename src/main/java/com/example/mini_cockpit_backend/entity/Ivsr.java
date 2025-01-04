package com.example.mini_cockpit_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "ivsr")
public class Ivsr {

    @Id
    private String productionNumber;
    private String colorCode;
    @Column(length = 1337)
    private String optionsString;
    private LocalDate actualProductionDate;
    private LocalDate purchaseAgreementDate;
    private LocalDate retailCountingDate;

    private String plannedHandoverWeek;
    private String expectedDeliveryWeek;

    private boolean sharepointData;
    private boolean ivsrData;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @ManyToOne
    @JoinColumn(name = "caseType_id")
    private CaseType caseType;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "sales_person_id")
    private SalesPerson salesPerson;

    public Ivsr() {

    }
}
