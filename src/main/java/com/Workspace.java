package com;
//An id
//Whether it is avalaiable

import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private int id;
    private String type;
    private boolean isAvailable;
    private List<Reservation> reservations = new ArrayList<>();

    public Workspace(int id, String type, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public String getType(){
        return type;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    @Override
    public String toString() {
        return "Workspace ID: " + id + ", Type: " + type + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}
