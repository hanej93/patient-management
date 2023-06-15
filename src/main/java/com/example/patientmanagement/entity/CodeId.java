package com.example.patientmanagement.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CodeId implements Serializable {

	private String codeId;
	private CodeGroup codeGroup;

}
