package com.example.andersantrainingspring;

import com.example.andersantrainingspring.service.WorkspaceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final WorkspaceService service;
    private final Scanner scan = new Scanner(System.in);

    public ConsoleRunner(WorkspaceService service) { this.service = service; }

    @Override
    public void run(String... args) {
        boolean running = true;
        while (running) {
            System.out.println("""
                === Main Menu ===
                1. List workspaces
                2. Add workspace
                3. Exit
                Choose: """);
            switch (scan.nextLine().trim()) {
                case "1" -> service.listAll().forEach(System.out::println);
                case "2" -> {
                    System.out.print("Type: ");
                    String type = scan.nextLine();
                    service.addWorkspace(type, true);
                }
                case "3" -> running = false;
                default  -> System.out.println("Invalid.");
            }
        }
    }
}
