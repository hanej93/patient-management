package com.example.patientmanagement.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.patientmanagement.dto.request.VisitCreateRequestDto;
import com.example.patientmanagement.dto.request.VisitUpdateRequestDto;
import com.example.patientmanagement.dto.response.VisitDto;
import com.example.patientmanagement.entity.Visit;
import com.example.patientmanagement.exception.VisitNotFoundException;
import com.example.patientmanagement.repository.VisitRepository;
import com.example.patientmanagement.service.VisitService;
import com.example.patientmanagement.mapper.VisitMapper;

import lombok.RequiredArgsConstructor;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

	private final VisitRepository visitRepository;

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
		Visit visit = VisitMapper.toEntity(requestDto);
		Visit createdVisit = visitRepository.save(visit);
		return VisitMapper.toDto(createdVisit);
	}

	@Override
	@Transactional
	public VisitDto updateVisit(Long visitId, VisitUpdateRequestDto requestDto) {
		Visit visit = visitRepository.findById(visitId)
			.orElseThrow(VisitNotFoundException::new);
		VisitMapper.updateVisit(visit, requestDto);
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
