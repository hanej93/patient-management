package com.example.patientmanagement.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Hospital {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long hospitalId;

	@Column(length = 45, nullable = false)
	private String hospitalName;

	@Column(length = 20, nullable = false)
	private String careInstitutionNumber;

	@Column(length = 10,nullable = false)
	private String directorName;

	@OneToMany(mappedBy = "hospital", cascade = ALL, orphanRemoval = true)
	private Set<Patient> patients = new HashSet<>();

	@OneToMany(mappedBy = "hospital", cascade = ALL, orphanRemoval = true)
	private List<Visit> visits = new ArrayList<>();

}
