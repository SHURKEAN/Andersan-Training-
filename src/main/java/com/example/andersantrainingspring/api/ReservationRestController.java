package com.example.andersantrainingspring.api;

import com.example.andersantrainingspring.domain.Reservation;
import com.example.andersantrainingspring.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    private final WorkspaceService service;

    public ReservationRestController(WorkspaceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationDto dto) {

        Reservation res = service.makeReservation(
                dto.customerName(), dto.workspaceId(),
                dto.resDate(), dto.startTime(), dto.endTime()
        );

        if (res == null) {
            return ResponseEntity.badRequest()
                    .body("Workspace not available or not found");
        }

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable int id) {
        return service.cancelReservation(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // simple DTO to receive JSON
    public record ReservationDto(String customerName,
                                 int workspaceId,
                                 String resDate,
                                 String startTime,
                                 String endTime) { }
}
