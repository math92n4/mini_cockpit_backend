package com.example.mini_cockpit_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesPerson {

    @Id
    private int id;
    private String name;

    @OneToMany(mappedBy = "salesPerson")
    private Set<Ivsr> ivsrSet = new HashSet<>();
}
