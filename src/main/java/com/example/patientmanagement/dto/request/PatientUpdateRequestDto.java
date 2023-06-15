package com.example.patientmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateRequestDto {

	@NotBlank(message = "Patient name is required")
	private String patientName;

	@NotBlank(message = "Gender code is required")
	private String genderCode;

	@NotBlank(message = "Date of birth is required")
	private String dateOfBirth;

	@NotBlank(message = "Mobile phone number is required")
	private String mobilePhoneNumber;

}
