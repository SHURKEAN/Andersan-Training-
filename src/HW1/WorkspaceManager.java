package HW1;

import HW1.Workspace;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceManager {

    private List<Workspace> workspaces;

    public WorkspaceManager() {
        workspaces = new ArrayList<>();
    }

    public void addWorkspace(Workspace workspace) {
        workspaces.add(workspace);
    }

    public void showWorkspaces() {
        for (Workspace ws : workspaces) {
            System.out.println(ws);
        }
    }

    public void removeWorkspaceById(int id) {
        workspaces.removeIf(ws -> ws.getId() == id);
    }

    public static void main(String[] args) {
        Workspace ws1 = new Workspace(1, "Private Office", true);
        Workspace ws2 = new Workspace(2, "Open Desk", false);

        WorkspaceManager manager = new WorkspaceManager();
        manager.addWorkspace(ws1);
        manager.addWorkspace(ws2);

        System.out.println("All Workspaces:");
        manager.showWorkspaces();

        manager.removeWorkspaceById(1);

        System.out.println("After removal:");
        manager.showWorkspaces();
    }
}
