package com.example.andersantrainingspring.controller;

import com.example.andersantrainingspring.domain.Workspace;
import com.example.andersantrainingspring.service.WorkspaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService service;

    public WorkspaceController(WorkspaceService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("workspaces", service.getAll());
        return "workspace/list";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "workspace/add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String type,
                      @RequestParam(defaultValue = "true") boolean available) {
        service.addWorkspace(type, available);
        return "redirect:/workspaces";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        service.deleteWorkspace(id);
        return "redirect:/workspaces";
    }
}
