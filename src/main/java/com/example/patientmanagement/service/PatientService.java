package com.example.patientmanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.PatientSearchRequestDto;
import com.example.patientmanagement.dto.request.PatientUpdateRequestDto;
import com.example.patientmanagement.dto.response.PatientDto;
import com.example.patientmanagement.dto.response.PatientPagedResponseDto;
import com.example.patientmanagement.entity.Patient;

public interface PatientService {

	PatientDto getPatientById(Long patientId);

	List<PatientDto> getAllPatients();

	Page<PatientPagedResponseDto> getPagedPatients(PatientSearchRequestDto requestDto);

	PatientDto createPatient(PatientCreateRequestDto requestDto);

	PatientDto updatePatient(Long patientId, PatientUpdateRequestDto requestDto);

	void deletePatient(Long patientId);

}
