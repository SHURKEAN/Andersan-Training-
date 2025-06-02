package HW1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // To store the workspace
    static ArrayList<Workspace> workspaceList = new ArrayList<>();
    static ArrayList<Reservation> reservationList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running) {
            System.out.println("\n Welcome to Coworking Space Reservation System");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    adminMenu(scanner);
                    break;
                case 2:
                    customerMenu(scanner);
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using Coworking Space Reservation System");
                    break;
                default:
                    System.out.println("Try Again");

            }
        }
    }


    // Admin Menu
    public static void adminMenu(Scanner scanner) {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Add a new coworking space");
        System.out.println("2. View all coworking spaces");
        System.out.println("3. Back to main menu");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                addWorkspace(scanner);
                break;
            case 2:
                viewWorkspace();
                break;
            case 3:
                return;
            default:
                System.out.println("Try Again");
        }
    }

    public static void addWorkspace(Scanner scanner) {
        System.out.println("\n--- Add a new coworking space");
        System.out.println("Enter workspace ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter workspace name (Open/Private/Room): ");
        String type = scanner.nextLine();

        Workspace workspace = new Workspace(id, type, true);
        workspaceList.add(workspace);
        System.out.println("Workspace added");
    }

    public static void viewWorkspace() {
        if(workspaceList.isEmpty()) {
            System.out.println("There are no workspaces");
        }

        System.out.println("\n--- View all coworking spaces");
        for(Workspace w: workspaceList){
            System.out.println(w);
        }
    }

    public static void makeReservation(Scanner scanner) {
        System.out.println("\n--- Add a new reservation");
        System.out.println("Enter Your name: ");
        String name = scanner.nextLine();

        viewWorkspace();
        System.out.println("\nEnter");
        int id = scanner.nextInt();
        scanner.nextLine();


        Workspace selected = null;
        for(Workspace w: workspaceList){
            if(w.getId() == id && w.isAvailable()){
                selected = w;
                break;
            }
        }

        if(selected == null) {
            System.out.println("There is no such coworking space");
            return;
        }

        System.out.println("Enter date(YYYY-MM-DD): )");
        String date = scanner.nextLine();
        System.out.println("Enter start time(HH:MM): )");
        String start = scanner.nextLine();
        System.out.println("Enter end time: )");
        String end = scanner.nextLine();

        Reservation r = new Reservation(name, id, date, start, end);
        reservationList.add(r);
        selected.setAvailable(false);

        System.out.println("Reservation added" + r.getReservationId());
    }


    public static void viewCustomerReservations(Scanner scanner) {
        System.out.println("\nEnter your name: ");
        String name = scanner.nextLine();

        boolean found = false;

        for(Reservation r : reservationList){
            if(r.getCustomerName().equalsIgnoreCase(name)) {
                    System.out.println(r);
                    found = true;
                }
            }

        if(!found) {
            System.out.println("There is no such customer");
        }
    }


    public static void cancelReservation(Scanner scanner) {
        System.out.println("Enter your reservation ID to cancel: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Reservation toRemove = null;


        for(Reservation r : reservationList){
            if(r.getReservationId() == id) {
                toRemove = r;
                break;
            }
        }

        if(toRemove != null) {
            reservationList.remove(toRemove);

            for(Workspace w : workspaceList){
                if(w.getId() == toRemove.getWorkspaceId()) {
                    w.setAvailable(true);
                    break;
                }
            }
            System.out.println("Reservation cancelled.");
        }else {
            System.out.println("Reservation ID not found.");
        }


    }


    public static void customerMenu(Scanner scanner) {
        System.out.println("\n--- Customer Menu ---");
        boolean back = false;

        while(!back) {
            System.out.println("1. View available workspaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel a reservation");
            System.out.println("5. Back to main menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    viewWorkspace();
                    break;
                case 2:
                    makeReservation(scanner);
                    break;
                case 3:
                    viewCustomerReservations(scanner);
                    break;
                case 4:
                    cancelReservation(scanner);
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Try Again");
            }

        }


    }
}