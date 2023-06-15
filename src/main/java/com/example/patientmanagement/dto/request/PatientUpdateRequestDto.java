package com.example.patientmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateRequestDto {

	private String patientName;
	private String genderCode;
	private String dateOfBirth;
	private String mobilePhoneNumber;

}
