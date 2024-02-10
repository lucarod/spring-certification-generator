package com.lucarod.spring_certification.modules.students.useCases;

import org.springframework.stereotype.Service;

import com.lucarod.spring_certification.modules.students.dto.VerifyHasCertificationDTO;

@Service
public class VerifyHasCertificationUseCase {
  public boolean execute(VerifyHasCertificationDTO dto) {
    if (dto.getEmail().equals("luca12rodrigues@gmail.com") && dto.getTechnology().equals("React")) {
      return true;
    }

    return false;
  }
}
