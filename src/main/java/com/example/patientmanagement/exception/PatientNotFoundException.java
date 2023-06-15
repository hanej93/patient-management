package com.example.patientmanagement.exception;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends CustomException {

	private static final String MESSAGE = "해당 환자를 찾을 수 없습니다.";

	public PatientNotFoundException() {
		super(MESSAGE);
	}

	public PatientNotFoundException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
