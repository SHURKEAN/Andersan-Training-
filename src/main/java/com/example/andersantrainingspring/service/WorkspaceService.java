package com.example.andersantrainingspring.service;

import com.example.andersantrainingspring.domain.Reservation;
import com.example.andersantrainingspring.domain.Workspace;
import com.example.andersantrainingspring.factory.ReservationFactory;    // 👈 Factory
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
        Workspace w = workspaceRepo.save(new Workspace(type, available));

        /* -------------- Singleton pattern in action -------------- */
        NotificationService.getInstance()
                .sendNotification("Workspace " + w.getId() + " created");
        /* --------------------------------------------------------- */

        return w;
    }

    public boolean removeWorkspace(int id) {
        if (!workspaceRepo.existsById((long) id)) return false;
        workspaceRepo.deleteById((long) id);
        return true;
    }

    public List<Workspace> listAll()            { return workspaceRepo.findAll(); }
    public List<Workspace> listAvailable()      {
        return workspaceRepo.findAll().stream()
                .filter(Workspace::isAvailable)
                .toList();
    }

    /* ── Reservation ── */

    public Reservation makeReservation(String customer,
                                       int workspaceId,
                                       String date,
                                       String start,
                                       String end) {

        Workspace ws = workspaceRepo.findById((long) workspaceId)
                .filter(Workspace::isAvailable)
                .orElseThrow(() -> new IllegalStateException("Workspace not available"));

        ws.setAvailable(false);

        /* ---------- Factory Method pattern in action ---------- */
        Reservation res = ReservationFactory.createReservation(
                customer,
                ws,
                LocalDate.parse(date),
                LocalTime.parse(start),
                LocalTime.parse(end)
        );
        /* ------------------------------------------------------ */

        reservationRepo.save(res);

        /* notify */
        NotificationService.getInstance()
                .sendNotification("Reservation " + res.getId() +
                        " created for workspace " + ws.getId());

        return res;
    }

    public boolean cancelReservation(int reservationId) {
        return reservationRepo.findById((long) reservationId).map(res -> {
            Workspace ws = res.getWorkspace();
            ws.setAvailable(true);
            reservationRepo.delete(res);

            NotificationService.getInstance()
                    .sendNotification("Reservation " + res.getId() + " cancelled");
            return true;
        }).orElse(false);
    }

    /* (legacy stubs you haven't used) */
    public Object getAll()                { return null; }
    public boolean deleteWorkspace(int id){ return false; }
}
