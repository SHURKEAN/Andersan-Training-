package com.example.andersantrainingspring.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    /* ---------- columns ---------- */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String customerName;

    @Column(nullable = false)
    private LocalDate resDate;      // yyyy‑MM‑dd

    @Column(nullable = false)
    private LocalTime startTime;    // HH:mm

    @Column(nullable = false)
    private LocalTime endTime;      // HH:mm

    /* ---------- relation ---------- */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    @JsonBackReference               // prevents infinite JSON loop if you expose REST
    private Workspace workspace;

    /* ---------- constructors ---------- */

    protected Reservation() {}       // JPA spec

    public Reservation(String customerName,
                       Workspace workspace,
                       LocalDate resDate,
                       LocalTime startTime,
                       LocalTime endTime) {
        this.customerName = customerName;
        this.workspace    = workspace;
        this.resDate      = resDate;
        this.startTime    = startTime;
        this.endTime      = endTime;
    }

    /* ---------- getters ---------- */

    public Long       getId()           { return id; }
    public String     getCustomerName() { return customerName; }
    public Workspace  getWorkspace()    { return workspace; }
    public LocalDate  getResDate()      { return resDate; }
    public LocalTime  getStartTime()    { return startTime; }
    public LocalTime  getEndTime()      { return endTime; }

    /* ---------- mutators (optional) ---------- */

    public void setWorkspace(Workspace ws) { this.workspace = ws; }

    /* ---------- toString ---------- */

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customer='" + customerName + '\'' +
                ", workspace=" + (workspace != null ? workspace.getId() : null) +
                ", date=" + resDate +
                ", " + startTime + '-' + endTime +
                '}';
    }
}
