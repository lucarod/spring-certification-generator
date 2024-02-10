package com.lucarod.spring_certification.modules.questions.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(length = 50)
  private String technology;

  private String description;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @OneToMany
  @JoinColumn(name = "question_id")
  private List<AlternativesEntity> alternatives;
}
