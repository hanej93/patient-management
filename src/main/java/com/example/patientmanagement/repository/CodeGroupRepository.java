package com.example.patientmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.patientmanagement.entity.Code;
import com.example.patientmanagement.entity.CodeGroup;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, String> {
}
