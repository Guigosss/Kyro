package com.bulk.kyro.models.exercise;

import com.bulk.kyro.models.workoutset.WorkoutSetDto;

import java.util.List;

public record ExerciseWorkoutDto(
        ExerciseDto exercise,
        List<WorkoutSetDto> sets
) {
}
