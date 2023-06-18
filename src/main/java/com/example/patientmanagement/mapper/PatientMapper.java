package com.example.patientmanagement.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.response.PatientDto;
import com.example.patientmanagement.entity.Patient;

public class PatientMapper {

	public static PatientDto toDto(Patient patient) {
		return PatientDto.builder()
			.patientId(patient.getPatientId())
			.patientName(patient.getPatientName())
			.patientRegistrationNumber(patient.getPatientRegistrationNumber())
			.genderCode(patient.getGenderCode())
			.dateOfBirth(patient.getDateOfBirth())
			.mobilePhoneNumber(patient.getMobilePhoneNumber())
			.build();
	}

	public static List<PatientDto> toDtoList(List<Patient> patients) {
		return patients.stream()
			.map(PatientMapper::toDto)
			.collect(Collectors.toList());
	}

	public static Patient toEntity(PatientCreateRequestDto requestDto) {
		return Patient.builder()
			.patientName(requestDto.getPatientName())
			.genderCode(requestDto.getGenderCode())
			.dateOfBirth(requestDto.getDateOfBirth())
			.mobilePhoneNumber(requestDto.getMobilePhoneNumber())
			.build();
	}

}
