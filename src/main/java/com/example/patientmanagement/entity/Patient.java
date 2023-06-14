package com.example.patientmanagement.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Patient {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long patientId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Column(length = 45, nullable = false)
	private String patientName;

	@Column(length = 13, nullable = false)
	private String patientRegistrationNumber;

	@Column(length = 10, nullable = false)
	private String genderCode;

	@Column(length = 10, nullable = false)
	private String dateOfBirth;

	@Column(length = 20, nullable = false)
	private String mobilePhoneNumber;

	@OneToMany(mappedBy = "patient", cascade = ALL, orphanRemoval = true)
	private List<Visit> visits = new ArrayList<>();

	@Builder
	public Patient(Hospital hospital, String patientName, String patientRegistrationNumber, String genderCode,
		String dateOfBirth, String mobilePhoneNumber) {
		this.hospital = hospital;
		this.patientName = patientName;
		this.patientRegistrationNumber = patientRegistrationNumber;
		this.genderCode = genderCode;
		this.dateOfBirth = dateOfBirth;
		this.mobilePhoneNumber = mobilePhoneNumber;
	}
}
