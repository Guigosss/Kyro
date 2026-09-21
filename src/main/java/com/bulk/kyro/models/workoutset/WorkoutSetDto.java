package com.bulk.kyro.models.workoutset;

public record WorkoutSetDto(
        Long id,
        Double weight,
        Integer reps,
        Integer orderIndex
) {}
