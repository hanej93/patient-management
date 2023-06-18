package com.example.patientmanagement.entity.editor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientEditor {

	private String patientName;
	private String patientRegistrationNumber;
	private String genderCode;
	private String dateOfBirth;
	private String mobilePhoneNumber;

	public static PatientEditorBuilder builder() {
		return new PatientEditorBuilder();
	}

	public static class PatientEditorBuilder {
		private String patientName;
		private String patientRegistrationNumber;
		private String genderCode;
		private String dateOfBirth;
		private String mobilePhoneNumber;

		PatientEditorBuilder() {
		}

		public PatientEditorBuilder patientName(String patientName) {
			if (patientName != null) {
				this.patientName = patientName;
			}
			return this;
		}

		public PatientEditorBuilder patientRegistrationNumber(String patientRegistrationNumber) {
			if (patientRegistrationNumber != null) {
				this.patientRegistrationNumber = patientRegistrationNumber;
			}
			return this;
		}

		public PatientEditorBuilder genderCode(String genderCode) {
			if (genderCode != null) {
				this.genderCode = genderCode;
			}
			return this;
		}

		public PatientEditorBuilder dateOfBirth(String dateOfBirth) {
			if (dateOfBirth != null) {
				this.dateOfBirth = dateOfBirth;
			}
			return this;
		}

		public PatientEditorBuilder mobilePhoneNumber(String mobilePhoneNumber) {
			if (mobilePhoneNumber != null) {
				this.mobilePhoneNumber = mobilePhoneNumber;
			}
			return this;
		}

		public PatientEditor build() {
			return new PatientEditor(this.patientName, this.patientRegistrationNumber, this.genderCode, this.dateOfBirth, this.mobilePhoneNumber);
		}

		public String toString() {
			return "PatientEditor.PatientEditorBuilder(patientName=" + this.patientName + ", patientRegistrationNumber=" + this.patientRegistrationNumber + ", genderCode=" + this.genderCode + ", dateOfBirth=" + this.dateOfBirth + ", mobilePhoneNumber=" + this.mobilePhoneNumber + ")";
		}
	}

}
