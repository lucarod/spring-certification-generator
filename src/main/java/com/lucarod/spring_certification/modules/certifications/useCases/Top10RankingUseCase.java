package com.lucarod.spring_certification.modules.certifications.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucarod.spring_certification.modules.students.entities.CertificationStudentEntity;
import com.lucarod.spring_certification.modules.students.repositories.CertificationStudentRepository;

@Service
public class Top10RankingUseCase {
  @Autowired
  private CertificationStudentRepository certificationStudentRepository;

  public List<CertificationStudentEntity> execute() {
    return this.certificationStudentRepository.findTop10OrderByGradeDesc();
  }
}
