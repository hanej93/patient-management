package com.example.patientmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.patientmanagement.entity.CodeGroup;
import com.example.patientmanagement.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
