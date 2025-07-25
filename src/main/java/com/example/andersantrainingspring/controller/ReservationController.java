package com.example.andersantrainingspring.controller;

import com.example.andersantrainingspring.service.WorkspaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final WorkspaceService service;

    public ReservationController(WorkspaceService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String newReservationForm(@RequestParam(required = false) Integer workspaceId,
                                     Model model) {
        model.addAttribute("workspaces", service.getAll());
        model.addAttribute("selectedId", workspaceId);
        return "reservation/form";
    }

    @PostMapping("/new")
    public String createReservation(@RequestParam String customerName,
                                    @RequestParam int workspaceId,
                                    @RequestParam String resDate,
                                    @RequestParam String startTime,
                                    @RequestParam String endTime,
                                    Model model) {

        var res = service.makeReservation(customerName, workspaceId,
                resDate, startTime, endTime);

        if (res == null) {
            model.addAttribute("error", "Workspace not available or not found!");
            return newReservationForm(workspaceId, model);
        }
        return "redirect:/workspaces";
    }


    @PostMapping("/{resId}/cancel")
    public String cancel(@PathVariable int resId) {
        service.cancelReservation(resId);
        return "redirect:/workspaces";
    }
}
