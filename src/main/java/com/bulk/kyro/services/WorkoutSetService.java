package com.bulk.kyro.services;

import com.bulk.kyro.entities.ExerciseEntity;
import com.bulk.kyro.entities.WorkoutSessionEntity;
import com.bulk.kyro.entities.WorkoutSetEntity;
import com.bulk.kyro.mappers.WorkoutSetMapper;
import com.bulk.kyro.models.workoutset.WorkoutSetForm;
import com.bulk.kyro.repositories.ExerciseRepository;
import com.bulk.kyro.repositories.WorkoutSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkoutSetService {

    private final WorkoutSetRepository workoutSetRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSetMapper mapper;

    public List<WorkoutSetEntity> createSets(List<WorkoutSetForm> setsForm, WorkoutSessionEntity workoutSession){

        List<WorkoutSetEntity> sets = new ArrayList<>();

        Map<Long, Integer> orderIndexes = new HashMap<>();

        for (WorkoutSetForm form : setsForm) {
            ExerciseEntity exercise = exerciseRepository.findById(form.getExerciseId())
                    .orElseThrow(() -> new RuntimeException("Exercise not found"));

            Integer orderIndex = orderIndexes.merge(
                    form.getExerciseId(),
                    1,
                    Integer::sum
            );

            WorkoutSetEntity set = mapper.toEntity(form, orderIndex, workoutSession, exercise);

            sets.add(set);
        }

        return sets;
    }

    public void saveAllSets(List<WorkoutSetEntity> sets){
        workoutSetRepository.saveAll(sets);
    }
}
