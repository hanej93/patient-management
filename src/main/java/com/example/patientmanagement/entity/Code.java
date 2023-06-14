package com.example.patientmanagement.entity;

import static jakarta.persistence.FetchType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Code {

	@Id
	@GeneratedValue
	@Column(length = 10)
	private String codeId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "code_group_id")
	private CodeGroup codeGroup;

	@Column(length = 10, nullable = false)
	private String codeName;

}
