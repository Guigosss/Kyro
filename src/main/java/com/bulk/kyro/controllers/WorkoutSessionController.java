package com.bulk.kyro.controllers;

import com.bulk.kyro.entities.UserEntity;
import com.bulk.kyro.models.workoutsession.WorkoutSessionDto;
import com.bulk.kyro.models.workoutsession.WorkoutSessionForm;
import com.bulk.kyro.services.WorkoutSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal UserEntity user){
        List<WorkoutSessionDto> sessions = workoutSessionService.getSessionsByUser(user.getId());
        model.addAttribute("sessions", sessions);
        return "index";
    }

    @GetMapping("/{sessionId}")
    public String detailsSession(@PathVariable Long sessionId, Model model) {
        model.addAttribute("session", workoutSessionService.getSessionDetails(sessionId));
        return "details";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("session", new WorkoutSessionForm());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute(name = "session") WorkoutSessionForm sessionForm,
                         @AuthenticationPrincipal UserEntity user,
                         BindingResult result,
                         Model model){

        if (result.hasErrors()){
            model.addAttribute("session", sessionForm);
            return "create";
        }

        workoutSessionService.create(sessionForm, user.getId());
        return "redirect:/";
    }
}
