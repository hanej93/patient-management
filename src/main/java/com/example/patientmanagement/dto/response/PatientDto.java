package com.example.patientmanagement.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 직렬화 시 빈 값은 포함하지 않음
@JsonIgnoreProperties(ignoreUnknown = true) // 알 수 없는 프로퍼티 무시
public class PatientDto {

	private Long patientId;
	private String patientName;
	private String patientRegistrationNumber;
	private String genderCode;
	private String dateOfBirth;
	private String mobilePhoneNumber;
	private HospitalDto hospital;
	private List<VisitDto> visits;

}
