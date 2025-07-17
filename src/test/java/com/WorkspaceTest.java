package com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class WorkspaceManagerTest {

    private WorkspaceManager manager;

    @BeforeEach
    void setUp() {
        manager = new WorkspaceManager();
        manager.addWorkspace(new Workspace(1, "Private Office", true));
        manager.addWorkspace(new Workspace(2, "Open Desk", true));
        manager.addWorkspace(new Workspace(3, "Meeting Room", false));
    }

    @Test
    void addWorkspace_addsToCollection() {
        manager.addWorkspace(new Workspace(4, "Phone Booth", true));
        assertThat(manager.getWorkspaces()).hasSize(4);
        assertThat(manager.findWorkspaceById(4)).isPresent();
    }

    @Test
    void removeWorkspace_existingIdRemoves() {
        boolean removed = manager.removeWorkspaceById(1);
        assertThat(removed).isTrue();
        assertThat(manager.findWorkspaceById(1)).isEmpty();
        assertThat(manager.getWorkspaces()).hasSize(2);
    }

    @Test
    void removeWorkspace_missingIdReturnsFalseAndDoesNotChangeSize() {
        int before = manager.getWorkspaces().size();
        boolean removed = manager.removeWorkspaceById(999);
        assertThat(removed).isFalse();
        assertThat(manager.getWorkspaces()).hasSize(before);
    }

    @Test
    void addWorkspace_nullThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> manager.addWorkspace(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("workspace");
    }
}
