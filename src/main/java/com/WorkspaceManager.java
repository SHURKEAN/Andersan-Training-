package com;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkspaceManager {
    private List<Workspace> workspaces;

    public WorkspaceManager() {
        workspaces = new ArrayList<>();
    }

    public void addWorkspace(Workspace workspace) {
        workspaces.add(workspace);
    }

    public void showAvailableWorkspaces() {
        workspaces.stream()
                .filter(ws -> ws.isAvailable())  // Lambda for filtering
                .forEach(ws -> System.out.println(ws));  // Lambda for printing
    }

    public void showSortedAvailableWorkspaces() {
        List<Workspace> sortedWorkspaces = workspaces.stream()
                .filter(ws -> ws.isAvailable())
                .sorted((ws1, ws2) -> Integer.compare(ws1.getId(), ws2.getId()))
                .collect(Collectors.toList());

        sortedWorkspaces.forEach(System.out::println);
    }

    public Optional<Workspace> findWorkspaceById(int id) {
        return workspaces.stream()
                .filter(ws -> ws.getId() == id)
                .findFirst();
    }

    public void showWorkspaceById(int id) {
        Optional<Workspace> workspace = findWorkspaceById(id);
        workspace.ifPresentOrElse(
                ws -> System.out.println("Found: " + ws),
                () -> System.out.println("Workspace not found")
        );
    }

    public static void main(String[] args) {
        WorkspaceManager manager = new WorkspaceManager();
        Workspace ws1 = new Workspace(1, "Private Office", true);
        Workspace ws2 = new Workspace(2, "Open Desk", false);

        manager.addWorkspace(ws1);
        manager.addWorkspace(ws2);

        System.out.println("All Available Workspaces:");
        manager.showAvailableWorkspaces();

        System.out.println("\nSorted Available Workspaces:");
        manager.showSortedAvailableWorkspaces();

        System.out.println("\nSearching for Workspace with ID 1:");
        manager.showWorkspaceById(1);
    }
}
