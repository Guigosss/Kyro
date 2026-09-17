package com.bulk.kyro.models.workoutset;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class WorkoutSetForm {

    @NotNull
    @PositiveOrZero
    Double weight;

    @NotNull
    @Positive
    Integer reps;

    @NotNull
    private Long exerciseId;
}