package com.example.patientmanagement.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.patientmanagement.dto.request.VisitCreateRequestDto;
import com.example.patientmanagement.dto.request.VisitUpdateRequestDto;
import com.example.patientmanagement.dto.response.VisitDto;
import com.example.patientmanagement.entity.Hospital;
import com.example.patientmanagement.entity.Patient;
import com.example.patientmanagement.entity.Visit;
import com.example.patientmanagement.entity.editor.VisitEditor;
import com.example.patientmanagement.exception.HospitalNotFoundException;
import com.example.patientmanagement.exception.PatientNotFoundException;
import com.example.patientmanagement.exception.VisitNotFoundException;
import com.example.patientmanagement.mapper.VisitMapper;
import com.example.patientmanagement.repository.HospitalRepository;
import com.example.patientmanagement.repository.PatientRepository;
import com.example.patientmanagement.repository.VisitRepository;
import com.example.patientmanagement.service.VisitService;

import lombok.RequiredArgsConstructor;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

	private final VisitRepository visitRepository;
	private final PatientRepository patientRepository;

	@Override
	public VisitDto getVisitById(Long visitId) {
		Visit visit = visitRepository.findById(visitId)
			.orElseThrow(VisitNotFoundException::new);
		return VisitMapper.toDto(visit);
	}

	@Override
	public List<VisitDto> getAllVisits() {
		List<Visit> visits = visitRepository.findAll();
		return VisitMapper.toDtoList(visits);
	}

	@Override
	@Transactional
	public VisitDto createVisit(VisitCreateRequestDto requestDto) {
		Patient patient = patientRepository.findByIdAndHospitalId(requestDto.getPatientId(), requestDto.getHospitalId())
			.orElseThrow(PatientNotFoundException::new);

		Visit visit = VisitMapper.toEntity(requestDto);
		visit.setHospital(patient.getHospital());
		visit.setPatient(patient);
		Visit createdVisit = visitRepository.save(visit);
		return VisitMapper.toDto(createdVisit);
	}

	@Override
	@Transactional
	public VisitDto updateVisit(Long visitId, VisitUpdateRequestDto requestDto) {
		Visit visit = visitRepository.findById(visitId)
			.orElseThrow(VisitNotFoundException::new);

		VisitEditor visitEditor = visit.toEditor()
			.visitDate(requestDto.getVisitDate())
			.visitStatusCode(requestDto.getVisitStatusCode())
			.build();

		visit.edit(visitEditor);
		return VisitMapper.toDto(visit);
	}

	@Override
	@Transactional
	public void deleteVisit(Long visitId) {
		Visit visit = visitRepository.findById(visitId)
			.orElseThrow(VisitNotFoundException::new);
		visitRepository.delete(visit);
	}
}
