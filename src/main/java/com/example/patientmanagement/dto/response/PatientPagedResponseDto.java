package com.example.patientmanagement.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class PatientPagedResponseDto {

	private Long patientId;
	private String patientName;
	private String patientRegistrationNumber;
	private String genderCode;
	private String dateOfBirth;
	private String mobilePhoneNumber;
	private String recentlyVisited;

	@QueryProjection
	public PatientPagedResponseDto(Long patientId, String patientName, String patientRegistrationNumber,
		String genderCode,
		String dateOfBirth, String mobilePhoneNumber, String recentlyVisited) {
		this.patientId = patientId;
		this.patientName = patientName;
		this.patientRegistrationNumber = patientRegistrationNumber;
		this.genderCode = genderCode;
		this.dateOfBirth = dateOfBirth;
		this.mobilePhoneNumber = mobilePhoneNumber;
		this.recentlyVisited = recentlyVisited;
	}
}
