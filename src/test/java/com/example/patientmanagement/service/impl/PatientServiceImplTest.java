package com.example.patientmanagement.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.PatientSearchRequestDto;
import com.example.patientmanagement.dto.request.PatientUpdateRequestDto;
import com.example.patientmanagement.dto.response.PatientDto;
import com.example.patientmanagement.dto.response.PatientPagedResponseDto;
import com.example.patientmanagement.entity.Hospital;
import com.example.patientmanagement.entity.Patient;
import com.example.patientmanagement.exception.HospitalNotFoundException;
import com.example.patientmanagement.exception.PatientNotFoundException;
import com.example.patientmanagement.repository.HospitalRepository;
import com.example.patientmanagement.repository.PatientRepository;
import com.example.patientmanagement.repository.VisitRepository;
import com.github.javafaker.Faker;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private HospitalRepository hospitalRepository;

	@Mock
	private VisitRepository visitRepository;

	@InjectMocks
	private PatientServiceImpl patientService;

	private Patient patient;
	private Hospital hospital;

	private final Faker faker = new Faker(Locale.KOREA);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@BeforeEach
	public void setup() {
		hospital = createSampleHospital();
		patient = createSamplePatient(hospital);
	}

	private Hospital createSampleHospital() {
		Hospital hospital = Hospital.builder()
			.hospitalName(faker.medical().hospitalName())
			.careInstitutionNumber(faker.numerify("######"))
			.directorName(faker.name().fullName())
			.build();
		hospital.setHospitalId(1L);
		return hospital;
	}

	private Patient createSamplePatient(Hospital hospital) {
		Patient patient = Patient.builder()
			.hospital(hospital)
			.patientName(faker.name().fullName())
			.patientRegistrationNumber(faker.numerify("########"))
			.genderCode(faker.options().option("M", "F"))
			.dateOfBirth(dateFormat.format(faker.date().birthday()))
			.mobilePhoneNumber(faker.phoneNumber().phoneNumber())
			.build();
		patient.setPatientId(1L);
		return patient;
	}

	@Test
	@DisplayName("getPatientById - 존재하는 환자일 경우, 해당 환자 정보를 반환한다")
	public void testGetPatientById_ExistingPatient_ShouldReturnPatientDto() {
		// Given
		when(patientRepository.findById(patient.getPatientId())).thenReturn(Optional.of(patient));

		// When
		PatientDto result = patientService.getPatientById(patient.getPatientId());

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getPatientId()).isEqualTo(patient.getPatientId());
		assertThat(result.getPatientName()).isEqualTo(patient.getPatientName());
		assertThat(result.getHospital().getHospitalId()).isEqualTo(hospital.getHospitalId());
		assertThat(result.getHospital().getHospitalName()).isEqualTo(hospital.getHospitalName());
	}

	@Test
	@DisplayName("getPatientById - 존재하지 않는 환자일 경우, PatientNotFoundException이 발생한다")
	public void testGetPatientById_NonExistingPatient_ShouldThrowPatientNotFoundException() {
		// Given
		when(patientRepository.findById(patient.getPatientId())).thenReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> patientService.getPatientById(patient.getPatientId()))
			.isInstanceOf(PatientNotFoundException.class);
	}

	@Test
	@DisplayName("getAllPatients - 환자가 존재할 경우, 모든 환자 정보를 반환한다")
	public void testGetAllPatients_ExistingPatients_ShouldReturnListOfPatientDto() {
		// Given
		List<Patient> patients = new ArrayList<>();
		patients.add(patient);
		Patient patient2 = createSamplePatient(hospital);
		patient2.setPatientId(2L);
		patients.add(patient2);

		when(patientRepository.findAll()).thenReturn(patients);

		// When
		List<PatientDto> result = patientService.getAllPatients();

		// Then
		assertThat(result).isNotNull();
		assertThat(result).hasSize(patients.size());
	}

	@Test
	@DisplayName("getAllPatients - 환자가 존재하지 않을 경우, 빈 리스트를 반환한다")
	public void testGetAllPatients_NoPatients_ShouldReturnEmptyList() {
		// Given
		when(patientRepository.findAll()).thenReturn(new ArrayList<>());

		// When
		List<PatientDto> result = patientService.getAllPatients();

		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("getPagedPatients - 유효한 요청 객체를 전달할 경우, 페이지화된 환자 정보를 반환한다")
	public void testGetPagedPatients_ValidRequestDto_ShouldReturnPageOfPatientPagedResponseDto() {
		// Given
		PatientSearchRequestDto requestDto = new PatientSearchRequestDto();
		PageImpl<PatientPagedResponseDto> pagedResponse = new PageImpl<>(new ArrayList<>());
		when(patientRepository.getPagedPatients(requestDto)).thenReturn(pagedResponse);

		// When
		Page<PatientPagedResponseDto> result = patientService.getPagedPatients(requestDto);

		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(pagedResponse);
	}

	@Test
	@DisplayName("createPatient - 유효한 요청 객체를 전달할 경우, 환자를 생성하고 생성된 환자 정보를 반환한다")
	public void testCreatePatient_ValidRequestDto_ShouldCreateAndReturnPatientDto() {
		// Given
		PatientCreateRequestDto requestDto = PatientCreateRequestDto.builder()
			.hospitalId(hospital.getHospitalId())
			.patientName(faker.name().fullName())
			.genderCode(faker.options().option("F", "M"))
			.dateOfBirth(dateFormat.format(faker.date().birthday()))
			.mobilePhoneNumber(faker.phoneNumber().phoneNumber())
			.build();

		when(hospitalRepository.findById(requestDto.getHospitalId())).thenReturn(Optional.of(hospital));
		when(patientRepository.existsByPatientRegistrationNumber(anyString())).thenReturn(false);

		// When
		PatientDto result = patientService.createPatient(requestDto);

		// Then
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("createPatient - 존재하지 않는 병원 ID를 전달할 경우, HospitalNotFoundException이 발생한다")
	public void testCreatePatient_InvalidHospitalId_ShouldThrowHospitalNotFoundException() {
		// Given
		PatientCreateRequestDto requestDto = PatientCreateRequestDto.builder()
			.hospitalId(100L)
			.patientName(faker.name().fullName())
			.genderCode(faker.options().option("F", "M"))
			.dateOfBirth(dateFormat.format(faker.date().birthday()))
			.mobilePhoneNumber(faker.phoneNumber().phoneNumber())
			.build();

		when(hospitalRepository.findById(requestDto.getHospitalId())).thenReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> patientService.createPatient(requestDto))
			.isInstanceOf(HospitalNotFoundException.class);
	}

	@Test
	@DisplayName("updatePatient - 존재하는 환자일 경우, 환자 정보를 업데이트하고 업데이트된 환자 정보를 반환한다")
	public void testUpdatePatient_ExistingPatient_ShouldUpdateAndReturnPatientDto() {
		// Given
		Long patientId = patient.getPatientId();
		PatientUpdateRequestDto requestDto = PatientUpdateRequestDto.builder()
			.patientName(faker.name().fullName())
			.genderCode(faker.options().option("F", "M"))
			.dateOfBirth(dateFormat.format(faker.date().birthday()))
			.mobilePhoneNumber(faker.phoneNumber().phoneNumber())
			.build();

		when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

		// When
		PatientDto result = patientService.updatePatient(patientId, requestDto);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getPatientId()).isEqualTo(patientId);
		assertThat(result.getPatientName()).isEqualTo(requestDto.getPatientName());
	}

	@Test
	@DisplayName("updatePatient - 존재하지 않는 환자일 경우, PatientNotFoundException이 발생한다")
	public void testUpdatePatient_NonExistingPatient_ShouldThrowPatientNotFoundException() {
		// Given
		Long patientId = 100L;
		PatientUpdateRequestDto requestDto = PatientUpdateRequestDto.builder()
			.patientName(faker.name().fullName())
			.genderCode(faker.options().option("F", "M"))
			.dateOfBirth(dateFormat.format(faker.date().birthday()))
			.mobilePhoneNumber(faker.phoneNumber().phoneNumber())
			.build();

		when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> patientService.updatePatient(patientId, requestDto))
			.isInstanceOf(PatientNotFoundException.class);
	}

	@Test
	@DisplayName("deletePatient - 존재하는 환자일 경우, 환자를 삭제한다")
	public void testDeletePatient_ExistingPatient_ShouldDeletePatient() {
		// Given
		Long patientId = patient.getPatientId();
		when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

		// When
		assertThatCode(() -> patientService.deletePatient(patientId)).doesNotThrowAnyException();

		// Then
		verify(patientRepository, times(1)).delete(patient);
	}

	@Test
	@DisplayName("deletePatient - 존재하지 않는 환자일 경우, PatientNotFoundException이 발생한다")
	public void testDeletePatient_NonExistingPatient_ShouldThrowPatientNotFoundException() {
		// Given
		Long patientId = 99L;
		when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> patientService.deletePatient(patientId))
			.isInstanceOf(PatientNotFoundException.class);
		verify(patientRepository, never()).delete(any(Patient.class));
	}
}