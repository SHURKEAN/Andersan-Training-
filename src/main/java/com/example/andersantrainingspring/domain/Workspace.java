package com.example.andersantrainingspring.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workspace")
public class Workspace {

    // ── Columns ────────────────────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    @Column(name = "is_available")
    private boolean available = true;

    // ── Relations ──────────────────────────────────────
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Reservation> reservations = new ArrayList<>();

    // ── Constructors ───────────────────────────────────
    protected Workspace() { /* for JPA */ }

    public Workspace(String type, boolean available) {
        this.type = type;
        this.available = available;
    }

    public Workspace(int id, String type, boolean available) {
        this.id = id;
        this.type = type;
        this.available = available;
    }

    // ── Domain logic ──────────────────────────────────
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setWorkspace(this);
        available = false;
    }

    // ── Getters / Setters ─────────────────────────────
    public int getId() { return id; }
    public String getType() { return type; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    // ── Overrides ─────────────────────────────────────
    @Override
    public String toString() {
        return "Workspace{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", available=" + available +
                '}';
    }
}
