package com.example.andersantrainingspring.repository;

import com.example.andersantrainingspring.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {


}
