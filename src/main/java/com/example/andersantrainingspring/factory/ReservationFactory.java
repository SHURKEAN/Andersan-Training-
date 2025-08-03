package com.example.andersantrainingspring.factory;

import com.example.andersantrainingspring.domain.Reservation;
import com.example.andersantrainingspring.domain.Workspace;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationFactory {

    public static Reservation createReservation(String customerName,
                                                Workspace workspace,
                                                LocalDate resDate,
                                                LocalTime startTime,
                                                LocalTime endTime) {

        return new Reservation(customerName, workspace, resDate, startTime, endTime);
    }
}
