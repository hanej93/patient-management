package com.example.patientmanagement.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitCreateRequestDto {

	private Long hospitalId;
	private Long patientId;
	private LocalDateTime visitDate;
	private String visitStatusCode;

}
