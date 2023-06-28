package com.example.patientmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.patientmanagement.entity.Patient;
import com.example.patientmanagement.repository.custom.PatientRepositoryCustom;

public interface PatientRepository extends JpaRepository<Patient, Long>, PatientRepositoryCustom {

	boolean existsByPatientRegistrationNumber(String patientRegistrationNumber);

	@Query("select p from Patient p where p.patientId = :patientId and p.hospital.hospitalId = :hospitalId")
	Optional<Patient> findByIdAndHospitalId(@Param("patientId") Long patientId, @Param("hospitalId") Long hospitalId);
}
