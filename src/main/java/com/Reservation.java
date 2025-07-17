package com;

public class Reservation {
    private static int nextId = 1;
    private final int reservationId;
    private String customerName;
    private int workspaceId;
    private String date;
    private String startTime;
    private String endTime;

    public Reservation(String customerName, int workspaceId, String date, String startTime, String endTime) {
        this.reservationId = nextId++;
        this.customerName = customerName;
        this.workspaceId = workspaceId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public int getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + "\nCustomer Name: " + customerName + "\nWorkspace ID: " + workspaceId + "\nDate: " + date + "\nStart: " + startTime + "\nEnd: " + endTime;
    }
}
