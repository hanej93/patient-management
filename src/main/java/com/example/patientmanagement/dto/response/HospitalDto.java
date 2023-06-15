package com.example.patientmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {

	private Long hospitalId;
	private String hospitalName;
	private String careInstitutionNumber;
	private String directorName;

}
