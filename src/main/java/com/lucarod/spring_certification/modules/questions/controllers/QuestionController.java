package com.lucarod.spring_certification.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucarod.spring_certification.modules.questions.dto.AlternativeResultDTO;
import com.lucarod.spring_certification.modules.questions.dto.QuestionResultDTO;
import com.lucarod.spring_certification.modules.questions.entities.AlternativesEntity;
import com.lucarod.spring_certification.modules.questions.entities.QuestionEntity;
import com.lucarod.spring_certification.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {
  @Autowired
  private QuestionRepository questionRepository;

  @GetMapping("/technology/{technology}")
  public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
    var result = this.questionRepository.findByTechnology(technology);
    var toMap = result.stream().map(question -> mapQuestionToDTO(question)).collect(Collectors.toList());
    return toMap;
  }

  static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
    var questionResultDTO = QuestionResultDTO.builder().id(question.getId()).technology(question.getTechnology())
        .description(question.getDescription())
        .build();

    var alternativesResultDTO = question.getAlternatives().stream()
        .map(alternative -> mapAlternativeToDTO(alternative)).collect(Collectors.toList());

    questionResultDTO.setAlternatives(alternativesResultDTO);

    return questionResultDTO;
  }

  static AlternativeResultDTO mapAlternativeToDTO(AlternativesEntity alternative) {
    return AlternativeResultDTO.builder().id(alternative.getId()).description(alternative.getDescription()).build();
  }
}
