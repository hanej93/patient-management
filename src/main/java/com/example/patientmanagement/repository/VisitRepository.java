package com.example.patientmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.patientmanagement.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
