package com.example.andersantrainingspring.service;

import com.example.andersantrainingspring.domain.Reservation;
import com.example.andersantrainingspring.domain.Workspace;
import com.example.andersantrainingspring.repository.ReservationRepository;
import com.example.andersantrainingspring.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service               // ← makes it a Spring bean
@Transactional         // ← each public method runs in a DB txn
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepo;
    private final ReservationRepository reservationRepo;

    public WorkspaceService(WorkspaceRepository workspaceRepo,
                            ReservationRepository reservationRepo) {
        this.workspaceRepo = workspaceRepo;
        this.reservationRepo = reservationRepo;
    }

    /* ────── Workspace operations ────── */

    public Workspace addWorkspace(String type, boolean available) {
        return workspaceRepo.save(new Workspace(type, available));
    }

    public boolean removeWorkspace(int id) {
        if (!workspaceRepo.existsById((long) id)) return false;
        workspaceRepo.deleteById((long) id);
        return true;
    }

    public List<Workspace> listAll() {
        return workspaceRepo.findAll();
    }

    public List<Workspace> listAvailable() {
        return workspaceRepo.findAll()
                .stream()
                .filter(Workspace::isAvailable)
                .toList();
    }

    /* ────── Reservation operations ────── */

    public Reservation makeReservation(String customer,
                                       int workspaceId,
                                       String date,
                                       String start,
                                       String end) {

        Workspace ws = workspaceRepo.findById((long) workspaceId)
                .filter(Workspace::isAvailable)
                .orElseThrow(() -> new IllegalStateException("Not available"));

        ws.setAvailable(false); // flip flag
        Reservation res = new Reservation(customer, ws,
                java.time.LocalDate.parse(date),
                java.time.LocalTime.parse(start),
                java.time.LocalTime.parse(end));
        return reservationRepo.save(res);
    }

    public boolean cancelReservation(int reservationId) {
        return reservationRepo.findById((long) reservationId).map(res -> {
            Workspace ws = res.getWorkspace();
            ws.setAvailable(true);
            reservationRepo.delete(res);
            return true;
        }).orElse(false);
    }


}
