package com.bulk.kyro.repositories;

import com.bulk.kyro.entities.MuscleGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroupEntity, Long> {

}
