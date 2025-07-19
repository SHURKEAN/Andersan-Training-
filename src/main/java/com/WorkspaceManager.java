package com;

import com.db.ReservationDao;
import com.db.WorkspaceDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WorkspaceManager {

    private final WorkspaceDao   workspaceDao   = new WorkspaceDao();
    private final ReservationDao reservationDao = new ReservationDao();

    /** in‑memory cache for quick menu display */
    private List<Workspace> workspaces = new ArrayList<>();

    public WorkspaceManager() {
        reloadFromDb();
    }

    /* ------------ sync helper ------------ */

    public void reloadFromDb() {
        workspaces = new ArrayList<>(workspaceDao.findAll());
    }

    /* ------------ workspace ops ------------ */

    public void addWorkspace(String type, boolean available) {
        int id = workspaceDao.insert(type, available);
        if (id > 0) reloadFromDb();
        else        System.out.println("Failed to add workspace.");
    }

    public boolean removeWorkspaceById(int id) {
        boolean ok = workspaceDao.delete(id);
        if (ok) reloadFromDb();
        return ok;
    }

    public void setWorkspaceAvailability(int id, boolean available) {
        workspaceDao.updateAvailability(id, available);
        reloadFromDb();
    }

    /* ------------ reservation ops ------------ */

    public int makeReservation(String customer,
                               int workspaceId,
                               String date,
                               String start,
                               String end) {

        var wsOpt = findWorkspaceById(workspaceId);
        if (wsOpt.isEmpty() || !wsOpt.get().isAvailable()) {
            System.out.println("Workspace not available.");
            return -1;
        }

        int resId = reservationDao.insert(customer, wsOpt.get(), date, start, end);
        reloadFromDb();
        return resId;
    }

    public boolean cancelReservation(int resId) {
        Workspace ws = reservationDao.delete(resId);
        if (ws != null) {                         // reservation existed
            workspaceDao.updateAvailability(ws.getId(), true);
            reloadFromDb();
            return true;
        }
        return false;
    }


    /* ------------ query & display ------------ */

    public List<Workspace> getWorkspaces() {
        return Collections.unmodifiableList(workspaces);
    }

    public List<Workspace> getAvailableWorkspaces() {
        return workspaces.stream()
                .filter(Workspace::isAvailable)
                .toList();
    }

    public Optional<Workspace> findWorkspaceById(int id) {
        return workspaces.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
    }

    public void showWorkspaces() {
        workspaces.forEach(System.out::println);
    }

    /* ------------ DAO accessors ------------ */

    public WorkspaceDao   getWorkspaceDao()   { return workspaceDao; }
    public ReservationDao getReservationDao() { return reservationDao; }
}
