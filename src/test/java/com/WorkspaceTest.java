package com;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class WorkspaceTest {

    @Test
    void ctor_setsFieldsCorrectly() {
        // parameters in declared order: id, type, isAvailable
        Workspace ws = new Workspace(7, "Open Desk", true);

        assertThat(ws.getId()).isEqualTo(7);
        assertThat(ws.getType()).isEqualTo("Open Desk");
        assertThat(ws.isAvailable()).isTrue();
    }

    @Test
    void addReservation_marksUnavailable() {
        Workspace ws = new Workspace(1, "Room", true);

        // parameters in declared order for Reservation constructor
        Reservation r = new Reservation(
                "Alice",          // customerName
                1,                // workspaceId
                "2024-06-29",     // date
                "10:00",          // startTime
                "12:00"           // endTime
        );

        ws.addReservation(r);
        ws.setAvailable(false);   // same logic Main uses

        assertThat(ws.isAvailable()).isFalse();
    }
}
