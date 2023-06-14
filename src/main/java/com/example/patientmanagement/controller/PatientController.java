package com.example.patientmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.PatientUpdateRequestDto;
import com.example.patientmanagement.dto.response.PatientDto;
import com.example.patientmanagement.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;

	@GetMapping("/{patientId}")
	public ResponseEntity<PatientDto> getPatientById(@PathVariable Long patientId) {
		PatientDto patientDto = patientService.getPatientById(patientId);
		return ResponseEntity.ok(patientDto);
	}

	@GetMapping
	public ResponseEntity<List<PatientDto>> getAllPatients() {
		List<PatientDto> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}

	@PostMapping
	public ResponseEntity<PatientDto> createPatient(@RequestBody PatientCreateRequestDto requestDto) {
		PatientDto createdPatient = patientService.createPatient(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
	}

	@PutMapping("/{patientId}")
	public ResponseEntity<PatientDto> updatePatient(@PathVariable Long patientId,
		@RequestBody PatientUpdateRequestDto requestDto) {
		PatientDto updatedPatient = patientService.updatePatient(patientId, requestDto);
		return ResponseEntity.ok(updatedPatient);
	}

	@DeleteMapping("/{patientId}")
	public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
		patientService.deletePatient(patientId);
		return ResponseEntity.noContent().build();
	}

}
