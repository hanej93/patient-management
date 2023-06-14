package com.example.patientmanagement.service;

import java.util.List;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.PatientUpdateRequestDto;
import com.example.patientmanagement.dto.response.PatientDto;
import com.example.patientmanagement.entity.Patient;

public interface PatientService {

	PatientDto getPatientById(Long patientId);

	List<PatientDto> getAllPatients();

	PatientDto createPatient(PatientCreateRequestDto requestDto);

	PatientDto updatePatient(Long patientId, PatientUpdateRequestDto requestDto);

	void deletePatient(Long patientId);

}
