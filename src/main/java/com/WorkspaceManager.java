package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WorkspaceManager {

    private final List<Workspace> workspaces = new ArrayList<>();

    public void addWorkspace(Workspace workspace) {
        if (workspace == null) {
            throw new IllegalArgumentException("workspace cannot be null");
        }
        workspaces.add(workspace);
    }

    public boolean removeWorkspaceById(int id) {
        return workspaces.removeIf(ws -> ws.getId() == id);
    }

    public List<Workspace> getWorkspaces() {
        return Collections.unmodifiableList(workspaces);
    }

    public Optional<Workspace> findWorkspaceById(int id) {
        return workspaces.stream().filter(ws -> ws.getId() == id).findFirst();
    }

    public void showWorkspaces() {
        workspaces.forEach(System.out::println);
    }
}
