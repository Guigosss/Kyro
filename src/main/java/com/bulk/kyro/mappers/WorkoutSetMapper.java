package com.bulk.kyro.mappers;

import com.bulk.kyro.entities.ExerciseEntity;
import com.bulk.kyro.entities.WorkoutSessionEntity;
import com.bulk.kyro.entities.WorkoutSetEntity;
import com.bulk.kyro.models.workoutset.WorkoutSetDto;
import com.bulk.kyro.models.workoutset.WorkoutSetForm;
import org.springframework.stereotype.Component;

@Component
public class WorkoutSetMapper {

    public WorkoutSetDto toDto(WorkoutSetEntity entity){
        return new WorkoutSetDto(
                entity.getId(),
                entity.getWeight(),
                entity.getReps(),
                entity.getOrderIndex()
        );
    }

    public WorkoutSetEntity toEntity(WorkoutSetForm form,
                                     Integer orderIndex,
                                     WorkoutSessionEntity ws,
                                     ExerciseEntity exercise) {
        return new WorkoutSetEntity(
                form.getWeight(),
                form.getReps(),
                orderIndex,
                ws,
                exercise
        );
    }
}
