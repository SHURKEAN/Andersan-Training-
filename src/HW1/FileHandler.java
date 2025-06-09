package HW1;

import java.io.*;
import java.util.*;

public class FileHandler {

    public static void saveReservations(List<Reservation> reservations, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Reservation res : reservations) {
                // Format: customerName|workspaceId|date|startTime|endTime
                writer.write(res.getCustomerName() + "|" + res.getWorkspaceId() + "|" + res.getDate() + "|" + res.getStartTime() + "|" + res.getEndTime());
                writer.newLine();
            }
        }
    }

    public static List<Reservation> loadReservations(String filename) throws IOException {
        List<Reservation> reservations = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return reservations;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 5) {
                    String customerName = parts[0];
                    int workspaceId = Integer.parseInt(parts[1]);
                    String date = parts[2];
                    String startTime = parts[3];
                    String endTime = parts[4];
                    reservations.add(new Reservation(customerName, workspaceId, date, startTime, endTime));
                }
            }
        }
        return reservations;
    }
}
