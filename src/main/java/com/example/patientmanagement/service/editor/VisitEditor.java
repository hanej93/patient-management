package com.example.patientmanagement.service.editor;

import java.time.LocalDateTime;

import com.example.patientmanagement.entity.Visit;

public class VisitEditor {
	private final Visit visit;

	public VisitEditor(Visit visit) {
		this.visit = visit;
	}

	public VisitEditor visitDate(LocalDateTime visitDate) {
		visit.setVisitDate(visitDate);
		return this;
	}

	public VisitEditor visitStatusCode(String visitStatusCode) {
		visit.setVisitStatusCode(visitStatusCode);
		return this;
	}

	public Visit applyChanges() {
		return visit;
	}
}
