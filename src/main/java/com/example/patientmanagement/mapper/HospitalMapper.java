package com.example.patientmanagement.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.patientmanagement.dto.response.HospitalDto;
import com.example.patientmanagement.entity.Hospital;

public class HospitalMapper {

	public static HospitalDto toDto(Hospital hospital) {
		return HospitalDto.builder()
			.hospitalId(hospital.getHospitalId())
			.hospitalName(hospital.getHospitalName())
			.careInstitutionNumber(hospital.getCareInstitutionNumber())
			.directorName(hospital.getDirectorName())
			.build();
	}

	public static List<HospitalDto> toDtoList(List<Hospital> hospitals) {
		return hospitals.stream()
			.map(HospitalMapper::toDto)
			.collect(Collectors.toList());
	}

}
