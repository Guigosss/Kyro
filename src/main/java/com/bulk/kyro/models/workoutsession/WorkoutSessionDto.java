package com.bulk.kyro.models.workoutsession;

import com.bulk.kyro.models.exercise.ExerciseWorkoutDto;

import java.time.LocalDate;
import java.util.List;

public record WorkoutSessionDto(
        Long id,
        String nom,
        LocalDate date,
        String notes,
        Long userId,
        List<ExerciseWorkoutDto> exercises
) {}
