package com;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    /* ---------- columns ---------- */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String customerName;
    private String resDate;      // yyyy‑MM‑dd
    private String startTime;    // HH:mm
    private String endTime;      // HH:mm

    /* ---------- relation ---------- */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    /* ---------- constructors ---------- */

    protected Reservation() { }   // required by JPA

    public Reservation(String customerName,
                       Workspace workspace,
                       String resDate,
                       String startTime,
                       String endTime) {
        this.customerName = customerName;
        this.workspace    = workspace;
        this.resDate      = resDate;
        this.startTime    = startTime;
        this.endTime      = endTime;
    }

    public Reservation(String customerName, int workspaceId, String resDate, String startTime, String endTime) {
    }

    /* ---------- getters / setters ---------- */

    public int getId()               { return id; }
    public String getCustomerName()  { return customerName; }
    public Workspace getWorkspace()  { return workspace; }
    public String getResDate()       { return resDate; }
    public String getStartTime()     { return startTime; }
    public String getEndTime()       { return endTime; }

    public void setWorkspace(Workspace ws) { this.workspace = ws; }

    /* ---------- toString ---------- */

    @Override
    public String toString() {
        return String.format("""
                Reservation{id=%d, customer='%s', workspace=%d, date=%s, %s-%s}
                """,
                id, customerName,
                workspace != null ? workspace.getId() : null,
                resDate, startTime, endTime
        );
    }

    public int getWorkspaceId() {
        return 0;
    }

    public String getDate() {
        return "";
    }
}
