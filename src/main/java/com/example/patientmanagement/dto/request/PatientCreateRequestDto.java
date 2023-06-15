package com.example.patientmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateRequestDto {

	private String patientName;
	private Long hospitalId;
	private String genderCode;
	private String dateOfBirth;
	private String mobilePhoneNumber;

}
