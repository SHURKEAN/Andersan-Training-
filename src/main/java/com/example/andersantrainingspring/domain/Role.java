package com.example.andersantrainingspring.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    /* Constructors */
    protected Role() {}               // JPA
    public Role(String name) { this.name = name; }

    /* Getters */
    public Long getId() { return id; }
    public String getName() { return name; }

    /* Utility */
    @Override public String toString() { return name; }
}
