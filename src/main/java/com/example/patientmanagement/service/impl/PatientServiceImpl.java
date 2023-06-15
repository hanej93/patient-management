package com.example.patientmanagement.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.PatientSearchRequestDto;
import com.example.patientmanagement.dto.request.PatientUpdateRequestDto;
import com.example.patientmanagement.dto.response.PatientDto;
import com.example.patientmanagement.dto.response.PatientPagedResponseDto;
import com.example.patientmanagement.entity.Hospital;
import com.example.patientmanagement.entity.Patient;
import com.example.patientmanagement.entity.Visit;
import com.example.patientmanagement.exception.HospitalNotFoundException;
import com.example.patientmanagement.exception.PatientNotFoundException;
import com.example.patientmanagement.mapper.HospitalMapper;
import com.example.patientmanagement.mapper.PatientMapper;
import com.example.patientmanagement.mapper.VisitMapper;
import com.example.patientmanagement.repository.HospitalRepository;
import com.example.patientmanagement.repository.PatientRepository;
import com.example.patientmanagement.repository.VisitRepository;
import com.example.patientmanagement.service.PatientService;
import com.example.patientmanagement.utils.PatientUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

	private final PatientRepository patientRepository;
	private final HospitalRepository hospitalRepository;
	private final VisitRepository visitRepository;

	@Override
	public PatientDto getPatientById(Long patientId) {
		Patient patient = patientRepository.findById(patientId)
			.orElseThrow(PatientNotFoundException::new);
		PatientDto responseDto = PatientMapper.toDto(patient);

		List<Visit> visits = visitRepository.findByPatient(patient);
		responseDto.setVisits(VisitMapper.toDtoList(visits));
		responseDto.setHospital(HospitalMapper.toDto(patient.getHospital()));

		return responseDto;
	}

	@Override
	public List<PatientDto> getAllPatients() {
		List<Patient> patients = patientRepository.findAll();
		return PatientMapper.toDtoList(patients);
	}

	@Override
	public Page<PatientPagedResponseDto> getPagedPatients(PatientSearchRequestDto requestDto) {
		return patientRepository.getPagedPatients(requestDto);
	}

	@Override
	@Transactional
	public PatientDto createPatient(PatientCreateRequestDto requestDto) {
		Patient patient = PatientMapper.toEntity(requestDto);

		Hospital hospital = hospitalRepository.findById(requestDto.getHospitalId())
			.orElseThrow(HospitalNotFoundException::new);
		patient.setHospital(hospital);

		String patientRegistrationNumber = generateUniquePatientRegistrationNumber(hospital.getHospitalId(), 3);
		patient.setPatientRegistrationNumber(patientRegistrationNumber);

		patientRepository.save(patient);
		return PatientMapper.toDto(patient);
	}

	private String generateUniquePatientRegistrationNumber(Long hospitalId, int digit) {
		String registrationNumber = PatientUtils.generatePatientRegistrationNumber(hospitalId, digit);
		boolean isDuplicate = patientRepository.existsByPatientRegistrationNumber(registrationNumber);

		if (isDuplicate) {
			return generateUniquePatientRegistrationNumber(hospitalId, digit + 1);
		}
		return registrationNumber;
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
