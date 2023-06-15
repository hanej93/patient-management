package com.example.patientmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.patientmanagement.entity.Patient;
import com.example.patientmanagement.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
	List<Visit> findByPatient(Patient patient);

}
