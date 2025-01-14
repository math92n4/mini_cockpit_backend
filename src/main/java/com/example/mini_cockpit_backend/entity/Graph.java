package com.example.mini_cockpit_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph {

    @Id
    private int id;
    private String name;
    private boolean isMetabase;

    @ManyToMany
    @JoinTable(
            name = "user_graph",
            joinColumns = @JoinColumn(name = "graph_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> users;
}
