package com.lucarod.spring_certification.modules.questions.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "alternatives")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String description;

  @Column(name = "is_correct")
  private boolean isCorrect;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;
}
