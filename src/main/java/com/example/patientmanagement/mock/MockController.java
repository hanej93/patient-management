package com.example.patientmanagement.mock;

import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MockController {

	private final MockService mockService;

	@PostConstruct
	public void init() {
		// 코드그룹 생성
		mockService.generateCodeGroup();

		// 코드 생성
		mockService.generateCode();

		// 병원 생성
		mockService.generateHospital();

		// 환자 생성
		mockService.generatePatient();

		// 방문 생성
		mockService.generateVisit();
	}


}
