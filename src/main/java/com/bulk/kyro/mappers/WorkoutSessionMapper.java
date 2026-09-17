package com.bulk.kyro.mappers;

import com.bulk.kyro.entities.UserEntity;
import com.bulk.kyro.entities.WorkoutSessionEntity;
import com.bulk.kyro.models.workoutsession.WorkoutSessionForm;
import com.bulk.kyro.models.workoutsession.WorkoutSessionDto;
import org.springframework.stereotype.Component;

@Component
public class WorkoutSessionMapper {

    public WorkoutSessionDto toDto(WorkoutSessionEntity entity){
        return new WorkoutSessionDto(
                entity.getId(),
                entity.getNom(),
                entity.getDate(),
                entity.getNotes(),
                entity.getUser().getId()
        );
    }

    public WorkoutSessionEntity toEntity(WorkoutSessionForm form,
                                         UserEntity user){
        return new WorkoutSessionEntity(
                form.getNom(),
                form.getDate(),
                form.getNotes(),
                user
        );
    }
}
