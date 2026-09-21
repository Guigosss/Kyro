package com.bulk.kyro.controllers;

import com.bulk.kyro.entities.UserEntity;
import com.bulk.kyro.models.workoutsession.WorkoutSessionDto;
import com.bulk.kyro.models.workoutsession.WorkoutSessionForm;
import com.bulk.kyro.services.ExerciseService;
import com.bulk.kyro.services.WorkoutSessionService;
import com.bulk.kyro.services.WorkoutSetService;
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
    private final WorkoutSetService workoutSetService;
    private final ExerciseService exerciseService;

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal UserEntity user){
        List<WorkoutSessionDto> sessions = workoutSessionService.getSessionsByUser(user.getId());
        model.addAttribute("sessions", sessions);
        return "index";
    }

    @GetMapping("/{sessionId}")
    public String detailsSession(@PathVariable Long sessionId, Model model) {
        model.addAttribute("workoutSession", workoutSessionService.getSessionDetails(sessionId));
        model.addAttribute("sets", workoutSetService.getSetsBySession(sessionId));
        return "sessions/details";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("workoutSession", new WorkoutSessionForm());
        model.addAttribute("exercises", exerciseService.getAllExercises());
        return "sessions/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute(name = "session") WorkoutSessionForm sessionForm,
                         @AuthenticationPrincipal UserEntity user,
                         BindingResult result,
                         Model model){

        if (result.hasErrors()){
            model.addAttribute("workoutSession", sessionForm);
            return "sessions/create";
        }

        workoutSessionService.create(sessionForm, user.getId());
        return "redirect:/";
    }
}
