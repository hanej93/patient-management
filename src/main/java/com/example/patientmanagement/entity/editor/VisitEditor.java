package com.example.patientmanagement.entity.editor;

import java.time.LocalDateTime;

import com.example.patientmanagement.entity.Hospital;
import com.example.patientmanagement.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitEditor {

	private Hospital hospital;
	private Patient patient;
	private LocalDateTime visitDate;
	private String visitStatusCode;

	public static VisitEditorBuilder builder() {
		return new VisitEditorBuilder();
	}

	public static class VisitEditorBuilder {
		private Hospital hospital;
		private Patient patient;
		private LocalDateTime visitDate;
		private String visitStatusCode;

		VisitEditorBuilder() {
		}

		public VisitEditorBuilder hospital(Hospital hospital) {
			if (hospital != null) {
				this.hospital = hospital;
			}
			return this;
		}

		public VisitEditorBuilder patient(Patient patient) {
			if (patient != null) {
				this.patient = patient;
			}
			return this;
		}

		public VisitEditorBuilder visitDate(LocalDateTime visitDate) {
			if (visitDate != null) {
				this.visitDate = visitDate;
			}
			return this;
		}

		public VisitEditorBuilder visitStatusCode(String visitStatusCode) {
			if (visitStatusCode != null) {
				this.visitStatusCode = visitStatusCode;
			}
			return this;
		}

		public VisitEditor build() {
			return new VisitEditor(this.hospital, this.patient, this.visitDate, this.visitStatusCode);
		}

		public String toString() {
			return "VisitEditor.VisitEditorBuilder(hospital=" + this.hospital + ", patient=" + this.patient + ", visitDate=" + this.visitDate + ", visitStatusCode=" + this.visitStatusCode + ")";
		}
	}

}
