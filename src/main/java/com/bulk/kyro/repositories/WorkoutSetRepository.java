package com.bulk.kyro.repositories;

import com.bulk.kyro.entities.WorkoutSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutSetRepository extends JpaRepository<WorkoutSetEntity, Long> {

}
