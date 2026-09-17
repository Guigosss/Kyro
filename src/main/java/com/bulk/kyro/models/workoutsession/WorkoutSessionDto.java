package com.bulk.kyro.models.workoutsession;

import java.time.LocalDate;

public record WorkoutSessionDto(
        Long id,
        String nom,
        LocalDate date,
        String notes,
        Long userId
) {}
