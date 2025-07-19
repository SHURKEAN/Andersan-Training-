package com;

import com.db.ReservationDao;
import com.db.WorkspaceDao;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner SCAN = new Scanner(System.in);

    private static final WorkspaceManager manager = new WorkspaceManager();
    private static final WorkspaceDao workspaceDao = manager.getWorkspaceDao();
    private static final ReservationDao reservationDao = manager.getReservationDao();

    public static void main(String[] args) {
        System.out.println("=== Coworking Space Reservation ===");

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("Main Menu");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            switch (readLine().trim()) {
                case "1" -> adminMenu();
                case "2" -> customerMenu();
                case "3" -> running = false;
                default  -> System.out.println("Invalid choice.");
            }
        }

        System.out.println("Goodbye.");
    }

    /* -------------------- Admin -------------------- */

    private static void adminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Admin Menu ---");
            System.out.println("1. View all reservations");
            System.out.println("2. Add workspace");
            System.out.println("3. Remove workspace");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            switch (readLine().trim()) {
                case "1" -> adminShowAllReservations();
                case "2" -> adminAddWorkspace();
                case "3" -> adminRemoveWorkspace();
                case "4" -> back = true;
                default  -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminShowAllReservations() {
        List<Reservation> all = reservationDao.findAll();
        if (all.isEmpty()) {
            System.out.println("(no reservations)");
            return;
        }
        all.forEach(System.out::println);
    }

    private static void adminAddWorkspace() {
        System.out.print("Workspace type: ");
        String type = readLine().trim();
        if (type.isEmpty()) {
            System.out.println("Type required.");
            return;
        }
        int newId = workspaceDao.insert(type, true);
        if (newId > 0) {
            manager.reloadFromDb();
            System.out.println("Workspace added with ID: " + newId);
        } else {
            System.out.println("Failed to add workspace.");
        }
    }

    private static void adminRemoveWorkspace() {
        int id = readInt("Workspace ID to remove: ");
        if (id < 0) return;
        boolean deleted = workspaceDao.delete(id);
        if (deleted) {
            manager.reloadFromDb();
            System.out.println("Removed.");
        } else {
            System.out.println("No such workspace.");
        }
    }

    /* -------------------- Customer -------------------- */

    private static void customerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Customer Menu ---");
            System.out.println("1. View available spaces");
            System.out.println("2. Make reservation");
            System.out.println("3. Cancel reservation");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            switch (readLine().trim()) {
                case "1" -> customerShowAvailable();
                case "2" -> customerMakeReservation();
                case "3" -> customerCancelReservation();
                case "4" -> back = true;
                default  -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void customerShowAvailable() {
        var available = manager.getAvailableWorkspaces();
        if (available.isEmpty()) {
            System.out.println("(no available workspaces)");
            return;
        }
        available.forEach(System.out::println);
    }

    private static void customerMakeReservation() {
        System.out.print("Your name: ");
        String name = readLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name required.");
            return;
        }

        int wid = readInt("Workspace ID: ");
        if (wid < 0) return;

        System.out.print("Date (yyyy-mm-dd): ");
        String date = readLine().trim();
        System.out.print("Start (HH:mm): ");
        String start = readLine().trim();
        System.out.print("End   (HH:mm): ");
        String end = readLine().trim();

        int dbId = manager.makeReservation(name, wid, date, start, end);
        if (dbId > 0) {
            System.out.println("Reservation saved. ID = " + dbId);
        } else {
            System.out.println("Reservation failed.");
        }
    }

    private static void customerCancelReservation() {
        int rid = readInt("Reservation ID to cancel: ");
        if (rid < 0) return;
        boolean ok = manager.cancelReservation(rid);
        System.out.println(ok ? "Cancelled." : "Not found.");
    }

    /* -------------------- util -------------------- */

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String s = readLine().trim();
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Not a number.");
            return -1;
        }
    }

    private static String readLine() {
        if (!SCAN.hasNextLine()) {
            System.out.println("\n(no more input) exiting.");
            System.exit(0);
        }
        return SCAN.nextLine();
    }
}
