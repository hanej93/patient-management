package com.example.patientmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.patientmanagement.entity.Patient;
import com.example.patientmanagement.repository.custom.PatientRepositoryCustom;

public interface PatientRepository extends JpaRepository<Patient, Long>, PatientRepositoryCustom {

	boolean existsByPatientRegistrationNumber(String patientRegistrationNumber);
}
