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

import com.example.patientmanagement.dto.request.VisitCreateRequestDto;
import com.example.patientmanagement.dto.request.VisitUpdateRequestDto;
import com.example.patientmanagement.dto.response.VisitDto;
import com.example.patientmanagement.service.VisitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

	private final VisitService visitService;

	@GetMapping("/{visitId}")
	public ResponseEntity<VisitDto> getVisitById(@PathVariable Long visitId) {
		VisitDto visitDto = visitService.getVisitById(visitId);
		return ResponseEntity.ok(visitDto);
	}

	@GetMapping
	public ResponseEntity<List<VisitDto>> getAllVisits() {
		List<VisitDto> visits = visitService.getAllVisits();
		return ResponseEntity.ok(visits);
	}

	@PostMapping
	public ResponseEntity<VisitDto> createVisit(@RequestBody VisitCreateRequestDto requestDto) {
		VisitDto createdVisit = visitService.createVisit(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdVisit);
	}

	@PutMapping("/{visitId}")
	public ResponseEntity<VisitDto> updateVisit(@PathVariable Long visitId,
		@RequestBody VisitUpdateRequestDto requestDto) {
		VisitDto updatedVisit = visitService.updateVisit(visitId, requestDto);
		return ResponseEntity.ok(updatedVisit);
	}

	@DeleteMapping("/{visitId}")
	public ResponseEntity<Void> deleteVisit(@PathVariable Long visitId) {
		visitService.deleteVisit(visitId);
		return ResponseEntity.noContent().build();
	}

}
