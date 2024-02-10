package com.lucarod.spring_certification.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucarod.spring_certification.modules.students.dto.VerifyHasCertificationDTO;
import com.lucarod.spring_certification.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyHasCertificationUseCase {
  @Autowired
  private CertificationStudentRepository certificationStudentRepository;

  public boolean execute(VerifyHasCertificationDTO dto) {
    var queryResult = this.certificationStudentRepository.findByStudentEmailAndTechnology(dto.getEmail(),
        dto.getTechnology());
    return !queryResult.isEmpty();
  }
}
