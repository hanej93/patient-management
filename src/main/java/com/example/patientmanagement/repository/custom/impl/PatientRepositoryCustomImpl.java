package com.example.patientmanagement.repository.custom.impl;

import static com.example.patientmanagement.entity.QPatient.*;
import static com.example.patientmanagement.entity.QVisit.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.patientmanagement.dto.request.PatientSearchRequestDto;
import com.example.patientmanagement.dto.response.PatientPagedResponseDto;
import com.example.patientmanagement.dto.response.QPatientPagedResponseDto;
import com.example.patientmanagement.entity.QVisit;
import com.example.patientmanagement.repository.custom.PatientRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PatientRepositoryCustomImpl implements PatientRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<PatientPagedResponseDto> getPagedPatients(PatientSearchRequestDto requestDto) {

		List<PatientPagedResponseDto> content = jpaQueryFactory
			.select(new QPatientPagedResponseDto(
				patient.patientId, 									// 아이디
				patient.patientName, 								// 환자이름
				patient.patientRegistrationNumber, 					// 환자등록번호
				patient.genderCode, 								// 성별
				patient.dateOfBirth,								// 생년월일
				patient.mobilePhoneNumber,							// 휴대전화
				dateToChar(visit.visitDate).coalesce("-") 		// 최근방문
			))
			.from(patient)
			.leftJoin(patient.visits, visit)
			.where(
				getRecentlyVisited(visit.visitDate),
				getKeywordCondition(requestDto.getSchType(), requestDto.getKeyword())
			)
			.limit(requestDto.getPageSize())
			.offset(requestDto.getOffset())
			.orderBy(patient.patientId.desc())
			.fetch();

		Long total = jpaQueryFactory
			.select(patient.count())
			.from(patient)
			.where(
				getKeywordCondition(requestDto.getSchType(), requestDto.getKeyword())
			)
			.fetchOne();

		Pageable pageable = PageRequest.of(requestDto.getPageNo() - 1, requestDto.getPageSize());

		return new PageImpl<>(content, pageable, total);
	}

	private static BooleanExpression getRecentlyVisited(DateTimePath<LocalDateTime> visitDate) {
		QVisit subVisit = new QVisit("subVisit");

		return visitDate.eq(
			JPAExpressions.select(subVisit.visitDate.max())
				.from(subVisit)
				.where(subVisit.patient.eq(patient))
		).or(visitDate.isNull());
	}

	private StringTemplate dateToChar(DateTimePath<LocalDateTime> date) {
		return Expressions.stringTemplate("function('to_char', {0}, 'yyyy-MM-dd')", date);
	}

	private BooleanExpression getKeywordCondition(String schType, String keyword) {
		if (!StringUtils.hasText(keyword)) {
			return null;
		}
		Map<String, BooleanExpression> schTypeMap = new HashMap<>();
		schTypeMap.put("patientName", patient.patientName.contains(keyword));
		schTypeMap.put("patientRegistrationNumber", patient.patientRegistrationNumber.contains(keyword));
		schTypeMap.put("dateOfBirth", patient.dateOfBirth.contains(keyword));

		return schTypeMap.getOrDefault(schType, patient.patientName.contains(keyword));
	}

}
