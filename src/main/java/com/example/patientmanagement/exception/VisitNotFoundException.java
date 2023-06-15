package com.example.patientmanagement.exception;

import org.springframework.http.HttpStatus;

public class VisitNotFoundException extends CustomException {

	private static final String MESSAGE = "해당 환자 방문 기록을 찾을 수 없습니다.";

	public VisitNotFoundException() {
		super(MESSAGE);
	}

	public VisitNotFoundException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
