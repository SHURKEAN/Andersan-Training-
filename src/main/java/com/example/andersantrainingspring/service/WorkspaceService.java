package com.example.andersantrainingspring.service;

import com.example.andersantrainingspring.domain.Reservation;
import com.example.andersantrainingspring.domain.Workspace;
import com.example.andersantrainingspring.repository.ReservationRepository;
import com.example.andersantrainingspring.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepo;
    private final ReservationRepository reservationRepo;

    public WorkspaceService(WorkspaceRepository workspaceRepo,
                            ReservationRepository reservationRepo) {
        this.workspaceRepo = workspaceRepo;
        this.reservationRepo = reservationRepo;
    }

    /* ── Workspace ── */

    public Workspace addWorkspace(String type, boolean available) {
        return workspaceRepo.save(new Workspace(type, available));
    }

    public boolean removeWorkspace(int id) {
        if (!workspaceRepo.existsById((long) id)) {
            return false;
        }
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

    /* ── Reservation ── */

    public Reservation makeReservation(String customer,
                                       int workspaceId,
                                       String date,
                                       String start,
                                       String end) {

        Workspace workspace = workspaceRepo.findById((long) workspaceId)
                .filter(Workspace::isAvailable)
                .orElseThrow(() -> new IllegalStateException("Workspace not available"));

        workspace.setAvailable(false);

        Reservation reservation = new Reservation(
                customer,
                workspace,
                LocalDate.parse(date),
                LocalTime.parse(start),
                LocalTime.parse(end)
        );

        return reservationRepo.save(reservation);
    }

    public boolean cancelReservation(int reservationId) {
        reservationRepo.findById((long) reservationId).map(reservation -> {
            Workspace workspace = reservation.getWorkspace();
            workspace.setAvailable(true);
            reservationRepo.delete(reservation);
            return true;
        });
        return false;
    }

    public Object getAll() {
        return null;
    }

    public boolean deleteWorkspace(int id) {
        return false;
    }
}
