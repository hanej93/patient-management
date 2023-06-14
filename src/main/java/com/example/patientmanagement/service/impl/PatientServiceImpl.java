package com.example.patientmanagement.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.PatientUpdateRequestDto;
import com.example.patientmanagement.dto.response.PatientDto;
import com.example.patientmanagement.entity.Patient;
import com.example.patientmanagement.exception.PatientNotFoundException;
import com.example.patientmanagement.mapper.PatientMapper;
import com.example.patientmanagement.repository.PatientRepository;
import com.example.patientmanagement.service.PatientService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

	private final PatientRepository patientRepository;

	@Override
	public PatientDto getPatientById(Long patientId) {
		Patient patient = patientRepository.findById(patientId)
			.orElseThrow(PatientNotFoundException::new);
		return PatientMapper.toDto(patient);
	}

	@Override
	public List<PatientDto> getAllPatients() {
		List<Patient> patients = patientRepository.findAll();
		return PatientMapper.toDtoList(patients);
	}

	@Override
	@Transactional
	public PatientDto createPatient(PatientCreateRequestDto requestDto) {
		Patient patient = PatientMapper.toEntity(requestDto);
		Patient createdPatient = patientRepository.save(patient);
		return PatientMapper.toDto(createdPatient);
	}

	@Override
	@Transactional
	public PatientDto updatePatient(Long patientId, PatientUpdateRequestDto requestDto) {
		Patient patient = patientRepository.findById(patientId)
			.orElseThrow(PatientNotFoundException::new);
		PatientMapper.updatePatient(patient, requestDto);
		return PatientMapper.toDto(patient);
	}

	@Override
	@Transactional
	public void deletePatient(Long patientId) {
		Patient patient = patientRepository.findById(patientId)
			.orElseThrow(PatientNotFoundException::new);
		patientRepository.delete(patient);
	}

}
