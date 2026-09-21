package com.bulk.kyro.mappers;

import com.bulk.kyro.entities.ExerciseEntity;
import com.bulk.kyro.models.exercise.ExerciseDto;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public ExerciseDto toDto(ExerciseEntity entity){
        return new ExerciseDto(
                entity.getId(),
                entity.getName()
        );
    }
}
