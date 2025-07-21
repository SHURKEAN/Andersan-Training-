package com.example.andersantrainingspring.controller;

import com.example.andersantrainingspring.domain.Workspace;
import com.example.andersantrainingspring.service.WorkspaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService service;

    public WorkspaceController(WorkspaceService service) {
        this.service = service;
    }

    /** GET /api/workspaces  →  all workspaces */
    @GetMapping
    public List<Workspace> all() {
        return service.listAll();
    }

    /** POST /api/workspaces  (JSON body: {"type":"Desk","available":true}) */
    @PostMapping
    public Workspace add(@RequestBody Workspace dto) {
        return service.addWorkspace(dto.getType(), dto.isAvailable());
    }

    /** DELETE /api/workspaces/{id} */
    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        service.removeWorkspace(id);
    }
}
