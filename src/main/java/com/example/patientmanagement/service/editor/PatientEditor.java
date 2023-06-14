package com.example.patientmanagement.service.editor;

import com.example.patientmanagement.entity.Patient;

public class PatientEditor {

	private final Patient patient;

	public PatientEditor(Patient patient) {
		this.patient = patient;
	}

	public PatientEditor patientName(String patientName) {
		patient.setPatientName(patientName);
		return this;
	}

	public PatientEditor genderCode(String genderCode) {
		patient.setGenderCode(genderCode);
		return this;
	}

	public PatientEditor dateOfBirth(String dateOfBirth) {
		patient.setDateOfBirth(dateOfBirth);
		return this;
	}

	public PatientEditor mobilePhoneNumber(String mobilePhoneNumber) {
		patient.setMobilePhoneNumber(mobilePhoneNumber);
		return this;
	}

	public Patient applyChanges() {
		return patient;
	}
}
