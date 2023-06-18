package com.example.patientmanagement.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.time.LocalDateTime;

import com.example.patientmanagement.entity.editor.VisitEditor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Visit {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long visitId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@Column(nullable = false)
	private LocalDateTime visitDate;

	@Column(length = 10, nullable = false)
	private String visitStatusCode;

	@Builder
	public Visit(Hospital hospital, Patient patient, LocalDateTime visitDate, String visitStatusCode) {
		this.hospital = hospital;
		this.patient = patient;
		this.visitDate = visitDate;
		this.visitStatusCode = visitStatusCode;
	}

	public VisitEditor.VisitEditorBuilder toEditor() {
		return VisitEditor.builder()
			.hospital(hospital)
			.patient(patient)
			.visitDate(visitDate)
			.visitStatusCode(visitStatusCode);
	}

	public void edit(VisitEditor visitEditor) {
		hospital = visitEditor.getHospital();
		patient = visitEditor.getPatient();
		visitDate = visitEditor.getVisitDate();
		visitStatusCode = visitEditor.getVisitStatusCode();
	}

}
