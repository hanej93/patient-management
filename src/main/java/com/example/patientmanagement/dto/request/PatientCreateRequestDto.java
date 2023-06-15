package com.example.patientmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateRequestDto {

	@NotBlank(message = "Patient name is required")
	private String patientName;

	@NotNull(message = "Hospital ID is required")
	private Long hospitalId;

	@NotBlank(message = "Gender code is required")
	private String genderCode;

	@NotBlank(message = "Date of birth is required")
	private String dateOfBirth;

	@NotBlank(message = "Mobile phone number is required")
	private String mobilePhoneNumber;

}
