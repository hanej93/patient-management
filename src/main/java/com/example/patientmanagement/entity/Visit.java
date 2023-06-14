package com.example.patientmanagement.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Visit {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long visitId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@Column(nullable = false)
	private LocalDateTime visitDate;

	@Column(length = 10, nullable = false)
	private String visitStatusCode;

}
