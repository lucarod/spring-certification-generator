package com.lucarod.spring_certification.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucarod.spring_certification.modules.students.dto.VerifyHasCertificationDTO;
import com.lucarod.spring_certification.modules.students.useCases.VerifyHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
  @Autowired
  private VerifyHasCertificationUseCase verifyHasCertificationUseCase;

  @PostMapping("/verify-has-certification")
  public String verifyHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
    var result = this.verifyHasCertificationUseCase.execute(verifyHasCertificationDTO);
    if (result) {
      return "Usuário já fez a prova";
    }

    return "Usuário pode fazer a prova";
  }
}
