package com.bulk.kyro.repositories;

import com.bulk.kyro.entities.WorkoutSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSessionEntity, Long> {

    List<WorkoutSessionEntity> findByUserId(Long userId);
}
