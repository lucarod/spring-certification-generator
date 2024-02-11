package com.lucarod.spring_certification.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucarod.spring_certification.modules.questions.repositories.QuestionRepository;
import com.lucarod.spring_certification.modules.students.dto.StudentCertificationAnswerDTO;
import com.lucarod.spring_certification.modules.students.dto.VerifyHasCertificationDTO;
import com.lucarod.spring_certification.modules.students.entities.AnswersCertificationsEntity;
import com.lucarod.spring_certification.modules.students.entities.CertificationStudentEntity;
import com.lucarod.spring_certification.modules.students.entities.StudentEntity;
import com.lucarod.spring_certification.modules.students.repositories.CertificationStudentRepository;
import com.lucarod.spring_certification.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {
  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CertificationStudentRepository certificationStudentRepository;

  @Autowired
  private VerifyHasCertificationUseCase verifyHasCertificationUseCase;

  public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {
    var hasCertification = verifyHasCertificationUseCase
        .execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

    if (hasCertification) {
      throw new Exception("Você já tirou sua certificação");
    }

    var questions = questionRepository.findByTechnology(dto.getTechnology());
    List<AnswersCertificationsEntity> answersCertification = new ArrayList<>();
    var correctAnswers = new AtomicInteger(0);

    dto.getQuestionsAnswers().stream().forEach(questionAnswer -> {
      var question = questions.stream()
          .filter(q -> q.getId().equals(questionAnswer.getQuestionId()))
          .findFirst().get();

      var correctAnswer = question.getAlternatives().stream().filter(alternative -> alternative.isCorrect()).findFirst()
          .get();

      if (correctAnswer.getId().equals(questionAnswer.getAlternativeId())) {
        questionAnswer.setCorrect(true);
        correctAnswers.incrementAndGet();
      } else {
        questionAnswer.setCorrect(false);
      }

      var answersCertificationEntity = AnswersCertificationsEntity.builder().answerId(questionAnswer.getAlternativeId())
          .questionId(questionAnswer.getQuestionId()).isCorrect(questionAnswer.isCorrect()).build();

      answersCertification.add(answersCertificationEntity);
    });

    var student = studentRepository.findByEmail(dto.getEmail());
    UUID studentId;

    if (student.isEmpty()) {
      var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
      studentCreated = studentRepository.save(studentCreated);
      studentId = studentCreated.getId();
    } else {
      studentId = student.get().getId();
    }

    CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
        .studentId(studentId)
        .technology(dto.getTechnology())
        .grade(correctAnswers.get())
        .build();

    var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

    answersCertification.stream().forEach(answerCertification -> {
      answerCertification.setCertificationId(certificationStudentEntity.getId());
      answerCertification.setCertificationStudentEntity(certificationStudentEntity);
    });

    certificationStudentEntity.setAnswersCertificationEntity(answersCertification);

    return certificationStudentCreated;
  }
}
