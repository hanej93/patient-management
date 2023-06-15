package com.example.patientmanagement.dto.request;

import static java.lang.Math.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientSearchRequestDto {

	private static final int MAX_SIZE = 2000;

	@Builder.Default
	private Integer pageNo = 1;
	@Builder.Default
	private Integer pageSize = 10;
	private String schType;
	private String keyword;

	public long getOffset() {
		return (long) max(0, pageNo - 1) * min(pageSize, MAX_SIZE);
	}

}
