package HW1;

import HW1.Reservation;
import HW1.Workspace;

public class TestWorkspaceManager {

    public static void main(String[] args) {
        Workspace ws1 = new Workspace(1, "Private Office", true);
        Workspace ws2 = new Workspace(2, "Open Desk", true);
        Workspace ws3 = new Workspace(3, "Meeting Room", false);

        hw1.WorkspaceManager manager = new hw1.WorkspaceManager();

        manager.addWorkspace(ws1);
        manager.addWorkspace(ws2);
        manager.addWorkspace(ws3);

        Reservation reservation1 = new Reservation("John Doe", 2, "2023-07-10", "10:00", "12:00");
        Reservation reservation2 = new Reservation("Jane Smith", 1, "2023-07-11", "09:00", "11:00");

        ws2.addReservation(reservation1);
        ws1.addReservation(reservation2);

        System.out.println("All Workspaces with Reservations:");
        manager.showWorkspaces();

        manager.removeWorkspaceById(1);

        System.out.println("\nAfter removing workspace with ID 1:");
        manager.showWorkspaces();

        ws2.setAvailable(false);

        System.out.println("\nAfter setting workspace 2 as unavailable:");
        manager.showWorkspaces();
    }
}
