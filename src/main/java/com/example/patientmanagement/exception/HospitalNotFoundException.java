package com.example.patientmanagement.exception;

import org.springframework.http.HttpStatus;

public class HospitalNotFoundException extends CustomException {

	private static final String MESSAGE = "해당 병원을 찾을 수 없습니다.";

	public HospitalNotFoundException() {
		super(MESSAGE);
	}

	public HospitalNotFoundException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
