package com.lucarod.spring_certification.modules.questions.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucarod.spring_certification.modules.questions.entities.QuestionEntity;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
  List<QuestionEntity> findByTechnology(String technology);
}
