package com.example.patientmanagement.entity;

import static jakarta.persistence.FetchType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@IdClass(CodeId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {

	@Id
	@Column(length = 10)
	private String codeId;

	@Id
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "code_group_id")
	private CodeGroup codeGroup;

	@Column(length = 10, nullable = false)
	private String codeName;

	@Builder
	public Code(String codeId, CodeGroup codeGroup, String codeName) {
		this.codeId = codeId;
		this.codeGroup = codeGroup;
		this.codeName = codeName;
	}

	public void setCodeGroup(CodeGroup codeGroup) {
		this.codeGroup = codeGroup;
	}
}
