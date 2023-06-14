package com.example.patientmanagement.entity;

import static jakarta.persistence.CascadeType.*;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class CodeGroup {

	@Id
	@GeneratedValue
	@Column(length = 10)
	private String codeGroupId;

	@Column(length = 10, nullable = false)
	private String codeGroupName;

	@Column(length = 10, nullable = false)
	private String description;

	@OneToMany(mappedBy = "codeGroup", cascade = ALL, orphanRemoval = true)
	private Set<Code> codes = new HashSet<>();

}
