package com.example.andersantrainingspring.api;

import com.example.andersantrainingspring.domain.Workspace;
import com.example.andersantrainingspring.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceRestController {

    private final WorkspaceService service;

    public WorkspaceRestController(WorkspaceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Workspace> all() {
        return (List<Workspace>) service.getAll();
    }

    @PostMapping
    public ResponseEntity<Workspace> create(@RequestBody Workspace ws) {
        Workspace saved = service.addWorkspace(ws.getType(), ws.isAvailable());
        return ResponseEntity.created(URI.create("/api/workspaces/" + saved.getId()))
                .body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteWorkspace(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
