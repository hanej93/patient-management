package com.example.patientmanagement.repository.custom;

import org.springframework.data.domain.Page;

import com.example.patientmanagement.dto.request.PatientSearchRequestDto;
import com.example.patientmanagement.dto.response.PatientPagedResponseDto;

public interface PatientRepositoryCustom {

	Page<PatientPagedResponseDto> getPagedPatients(PatientSearchRequestDto requestDto);

}
