package com.example.patientmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

	private Long patientId;
	private String patientName;
	private String patientRegistrationNumber;
	private String genderCode;
	private String dateOfBirth;
	private String mobilePhoneNumber;

}
