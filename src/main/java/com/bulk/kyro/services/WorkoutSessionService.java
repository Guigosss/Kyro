package com.bulk.kyro.services;

import com.bulk.kyro.entities.UserEntity;
import com.bulk.kyro.entities.WorkoutSessionEntity;
import com.bulk.kyro.entities.WorkoutSetEntity;
import com.bulk.kyro.mappers.WorkoutSessionMapper;
import com.bulk.kyro.models.workoutsession.WorkoutSessionForm;
import com.bulk.kyro.models.workoutsession.WorkoutSessionDto;
import com.bulk.kyro.repositories.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final AuthService authService;
    private final WorkoutSetService workoutSetService;
    private final WorkoutSessionMapper mapper;

    public WorkoutSessionEntity getSessionDetails(Long id){
        return workoutSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    public void create(WorkoutSessionForm form, Long userId) {
        UserEntity user = authService.getUser(userId);

        WorkoutSessionEntity entity = mapper.toEntity(form, user);

        List<WorkoutSetEntity> sets = workoutSetService.createSets(form.getSets(), entity);
        workoutSetService.saveAllSets(sets);

        WorkoutSessionEntity saved = workoutSessionRepository.save(entity);

        mapper.toDto(saved);
    }

    public List<WorkoutSessionDto> getSessionsByUser(Long userId){
        return workoutSessionRepository.findByUserId(userId).stream().map(mapper::toDto).toList();
    }
}
