package com;
// HW1 initial commit for PR
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static ArrayList<Workspace> workspaceList = new ArrayList<>();
    static ArrayList<Reservation> reservationList = new ArrayList<>();
    static final String FILE_NAME = "reservations.txt";
    static PluginClassLoader pluginLoader = new PluginClassLoader("./plugins");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        workspaceList.add(new Workspace(1, "Open Desk", true));
        workspaceList.add(new Workspace(2, "Private Room", true));


        try {
            List<Reservation> loaded = FileHandler.loadReservations(FILE_NAME);
            reservationList.addAll(loaded);
            for (Reservation r : loaded) {
                for (Workspace w : workspaceList) {
                    if (w.getId() == r.getWorkspaceId()) {
                        w.addReservation(r);
                        w.setAvailable(false);
                    }
                }
            }
            System.out.println("Reservations loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }

        boolean running = true;

        while (running) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    adminMenu(scanner);
                    break;
                case "2":
                    customerMenu(scanner);
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }


        try {
            FileHandler.saveReservations(reservationList, FILE_NAME);
            System.out.println("Reservations saved.");
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }

        scanner.close();
    }

    private static void adminMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Reservations");
            System.out.println("2. Add Workspace");
            System.out.println("3. Remove Workspace");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    for (Reservation r : reservationList)
                        System.out.println(r);
                    break;
//                case "2":
//                    System.out.print("Enter ID: ");
//                    int id = Integer.parseInt(scanner.nextLine());
//                    System.out.print("Enter Type: ");
//                    String type = scanner.nextLine();
//                    workspaceList.add(new Workspace(id, type, true));
//                    break;


                case "2":
                    System.out.print("Enter fully-qualified class name (e.g., plugins.HotDeskVIP): ");
                    String fqcn = scanner.nextLine();
                    try {
                        Class<?> c = pluginLoader.loadClass(fqcn);
                        Object obj = c.getDeclaredConstructor().newInstance();
                        if (obj instanceof Workspace) {
                            workspaceList.add((Workspace) obj);
                            System.out.println("Plug-in workspace added: " + obj);
                        } else {
                            System.out.println("Class loaded but it is not a Workspace.");
                        }
                    } catch (Exception ex) {
                        System.out.println("Could not load workspace: " + ex.getMessage());
                    }
                    break;






                case "3":
                    System.out.print("Enter ID to remove: ");
                    int removeId = Integer.parseInt(scanner.nextLine());
                    workspaceList.removeIf(w -> w.getId() == removeId);
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    private static void customerMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View Available Spaces");
            System.out.println("2. Make Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    for (Workspace w : workspaceList)
                        if (w.isAvailable()) System.out.println(w);
                    break;
                case "2":
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Workspace ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Date (yyyy-mm-dd): ");
                    String date = scanner.nextLine();
                    System.out.print("Start Time: ");
                    String start = scanner.nextLine();
                    System.out.print("End Time: ");
                    String end = scanner.nextLine();

                    Reservation res = new Reservation(name, id, date, start, end);
                    reservationList.add(res);
                    for (Workspace w : workspaceList) {
                        if (w.getId() == id) {
                            w.addReservation(res);
                            w.setAvailable(false);
                        }
                    }

                    System.out.println("Reservation made with ID: " + res.getReservationId());
                    break;
                case "3":
                    System.out.print("Enter Reservation ID to cancel: ");
                    int resId = Integer.parseInt(scanner.nextLine());
                    reservationList.removeIf(r -> r.getReservationId() == resId);
                    System.out.println("Reservation cancelled.");
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

}
