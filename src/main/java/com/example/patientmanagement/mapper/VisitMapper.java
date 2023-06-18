package com.example.patientmanagement.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.patientmanagement.dto.request.VisitCreateRequestDto;
import com.example.patientmanagement.dto.response.VisitDto;
import com.example.patientmanagement.entity.Visit;

public class VisitMapper {

	public static VisitDto toDto(Visit visit) {
		return VisitDto.builder()
			.visitId(visit.getVisitId())
			.hospitalId(visit.getHospital().getHospitalId())
			.patientId(visit.getPatient().getPatientId())
			.visitDate(visit.getVisitDate())
			.visitStatusCode(visit.getVisitStatusCode())
			.build();
	}

	public static List<VisitDto> toDtoList(List<Visit> visits) {
		return visits.stream()
			.map(VisitMapper::toDto)
			.collect(Collectors.toList());
	}

	public static Visit toEntity(VisitCreateRequestDto requestDto) {
		return Visit.builder()
			.visitDate(requestDto.getVisitDate())
			.visitStatusCode(requestDto.getVisitStatusCode())
			.build();
	}

}