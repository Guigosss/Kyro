package com.bulk.kyro.services;

import com.bulk.kyro.entities.ExerciseEntity;
import com.bulk.kyro.mappers.ExerciseMapper;
import com.bulk.kyro.models.exercise.ExerciseDto;
import com.bulk.kyro.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper mapper;

    public ExerciseEntity getExercise(Long exerciseId){
        return exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
    }

    public List<ExerciseDto> getAllExercises() {
        return exerciseRepository.findAll().stream().map(mapper::toDto).toList();
    }
}
