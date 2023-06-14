package com.example.patientmanagement.service;

import java.util.List;

import com.example.patientmanagement.dto.request.VisitCreateRequestDto;
import com.example.patientmanagement.dto.request.VisitUpdateRequestDto;
import com.example.patientmanagement.dto.response.VisitDto;
import com.example.patientmanagement.entity.Visit;

public interface VisitService {

	VisitDto getVisitById(Long visitId);

	List<VisitDto> getAllVisits();

	VisitDto createVisit(VisitCreateRequestDto requestDto);

	VisitDto updateVisit(Long visitId, VisitUpdateRequestDto requestDto);

	void deleteVisit(Long visitId);

}
