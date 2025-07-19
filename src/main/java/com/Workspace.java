package com;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A physical or virtual workspace that can be reserved.
 */
@Entity
@Table(name = "workspace")
public class Workspace {

    /* ──────────────── Columns ──────────────── */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Desk, meeting-room, hot-desk, etc.
     */
    private String type;

    /**
     * True when at least one free slot is available.
     */
    @Column(name = "is_available")
    private boolean available = true;

    /* ─────────────── Relations ─────────────── */

    @OneToMany(
            mappedBy     = "workspace",
            cascade      = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Reservation> reservations = new ArrayList<>();

    /* ───────────── Constructors ────────────── */

    /** Required by JPA (must be package-private or protected). */
    protected Workspace() {
    }

    public Workspace(String type, boolean available) {
        this.type = type;
        this.available = available;
    }

    public Workspace(int id, String type, boolean available) {
        this.id = id;
        this.type = type;
        this.available = available;
    }

    /* ────────── Domain-level helpers ───────── */

    /**
     * Attach a reservation and mark the workspace as no longer
     * generally available.
     *
     * @param reservation a newly created reservation
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setWorkspace(this);
        this.available = false;
    }

    /* ────────────── Accessors ──────────────── */

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    /* ────────────── toString() ─────────────── */

    @Override
    public String toString() {
        return String.format(
                "Workspace{id=%d, type='%s', available=%s}",
                id, type, available
        );
    }
}
