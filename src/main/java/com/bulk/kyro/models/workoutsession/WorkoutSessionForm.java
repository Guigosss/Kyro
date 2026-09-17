package com.bulk.kyro.models.workoutsession;

import com.bulk.kyro.models.workoutset.WorkoutSetForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class WorkoutSessionForm {

    @NotBlank
    String nom;

    @NotBlank
    LocalDate date;

    @Size(max = 500)
    String notes;

    private List<WorkoutSetForm> sets = new ArrayList<>();
}
