package com;

import com.db.ReservationDao;
import com.db.WorkspaceDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WorkspaceManager {

    private final WorkspaceDao workspaceDao = new WorkspaceDao();
    private final ReservationDao reservationDao = new ReservationDao();
    private final List<Workspace> workspaces = new ArrayList<>();

    public WorkspaceManager() {
        reloadFromDb();
    }

    public void reloadFromDb() {
        workspaces.clear();
        workspaces.addAll(workspaceDao.findAll());
    }

    public void addWorkspace(Workspace workspace) {
        if (workspace == null) {
            throw new IllegalArgumentException("workspace cannot be null");
        }
        int newId = workspaceDao.insert(workspace.getType(), workspace.isAvailable());
        if (newId > 0) {
            workspaces.add(new Workspace(newId, workspace.getType(), workspace.isAvailable()));
        } else {
            System.out.println("Failed to insert workspace in DB.");
        }
    }

    public void addWorkspace(String type, boolean available) {
        addWorkspace(new Workspace(-1, type, available));
    }

    public boolean removeWorkspaceById(int id) {
        boolean deleted = workspaceDao.delete(id);
        if (deleted) {
            workspaces.removeIf(ws -> ws.getId() == id);
        }
        return deleted;
    }

    public void setWorkspaceAvailability(int id, boolean available) {
        workspaceDao.updateAvailability(id, available);
        reloadFromDb();
    }

    public int makeReservation(String customerName,
                               int workspaceId,
                               String date,
                               String start,
                               String end) {
        Reservation r = new Reservation(customerName, workspaceId, date, start, end);
        int resId = reservationDao.insert(r);
        if (resId > 0) {
            workspaceDao.updateAvailability(workspaceId, false);
            reloadFromDb();
        } else {
            System.out.println("Failed to create reservation.");
        }
        return resId;
    }

    public boolean cancelReservation(int reservationId) {
        Integer wid = reservationDao.findWorkspaceIdForReservation(reservationId);
        boolean deleted = reservationDao.delete(reservationId);
        if (deleted && wid != null) {
            workspaceDao.updateAvailability(wid, true);
            reloadFromDb();
        }
        return deleted;
    }

    public List<Workspace> getWorkspaces() {
        return Collections.unmodifiableList(workspaces);
    }

    public List<Workspace> getAvailableWorkspaces() {
        return workspaces.stream().filter(Workspace::isAvailable).toList();
    }

    public Optional<Workspace> findWorkspaceById(int id) {
        return workspaces.stream().filter(ws -> ws.getId() == id).findFirst();
    }

    public void showWorkspaces() {
        workspaces.forEach(System.out::println);
    }

    public WorkspaceDao getWorkspaceDao() {
        return workspaceDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }


}
