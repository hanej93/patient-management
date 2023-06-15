package com.example.patientmanagement.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.PatientSearchRequestDto;
import com.example.patientmanagement.dto.request.PatientUpdateRequestDto;
import com.example.patientmanagement.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "localhost", uriPort = 8080)
@ExtendWith(RestDocumentationExtension.class)
class PatientControllerDocTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetPatientById() throws Exception {
		Long patientId = 1L;

		mockMvc.perform(get("/api/patients/{patientId}", patientId)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("get-patient-by-id",
				pathParameters(
					parameterWithName("patientId").description("환자 아이디")
				),
				responseFields(
					fieldWithPath("patientId").description("환자 아이디"),
					fieldWithPath("patientName").description("환자이름"),
					fieldWithPath("patientRegistrationNumber").description("환자 등록 번호"),
					fieldWithPath("genderCode").description("성별 코드"),
					fieldWithPath("dateOfBirth").description("출생일"),
					fieldWithPath("mobilePhoneNumber").description("모바일 전화번호"),
					fieldWithPath("hospital.hospitalId").description("병원 ID"),
					fieldWithPath("hospital.hospitalName").description("병원명"),
					fieldWithPath("hospital.careInstitutionNumber").description("요양기관번호"),
					fieldWithPath("hospital.directorName").description("병원장명"),
					fieldWithPath("visits").description("방문 리스트").optional().type(JsonFieldType.ARRAY),
					fieldWithPath("visits[].visitId").description("방문 ID").optional().type(JsonFieldType.NUMBER),
					fieldWithPath("visits[].hospitalId").description("병원 ID").optional().type(JsonFieldType.NUMBER),
					fieldWithPath("visits[].patientId").description("환자 ID").optional().type(JsonFieldType.NUMBER),
					fieldWithPath("visits[].visitDate").description("방문 날짜").optional().type(JsonFieldType.STRING),
					fieldWithPath("visits[].visitStatusCode").description("방문 상태 코드").optional().type(JsonFieldType.STRING)
				)));
	}

	@Test
	public void testGetAllPatients() throws Exception {
		mockMvc.perform(get("/api/patients/all")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("get-all-patients",
				responseFields(
					fieldWithPath("[]").description("모든 환자 리스트").optional(),
					fieldWithPath("[].patientId").description("환자 아이디").optional(),
					fieldWithPath("[].patientName").description("환자이름").optional(),
					fieldWithPath("[].patientRegistrationNumber").description("환자 등록 번호").optional(),
					fieldWithPath("[].genderCode").description("성별 코드").optional(),
					fieldWithPath("[].dateOfBirth").description("출생일").optional(),
					fieldWithPath("[].mobilePhoneNumber").description("모바일 전화번호").optional()
				)));
	}

	@Test
	public void testGetPagedPatients() throws Exception {

		mockMvc.perform(get("/api/patients")
				.param("pageNo", "1")
				.param("pageSize", "15")
				.param("schType", "patientName")
				.param("keyword", "이"))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("get-paged-patients",
				pathParameters(
					parameterWithName("pageNo").description("현재 페이지 번호").optional(),
					parameterWithName("pageSize").description("한 페이지 사이즈").optional(),
					parameterWithName("schType").description("검색 조건").optional(),
					parameterWithName("keyword").description("검색 키워드").optional()
				),
				responseFields(
					fieldWithPath("content[]").description("페이징된 환자 리스트").optional(),
					fieldWithPath("content[].patientId").type(JsonFieldType.NUMBER).description("환자 ID"),
					fieldWithPath("content[].patientName").type(JsonFieldType.STRING).description("환자 이름"),
					fieldWithPath("content[].patientRegistrationNumber").type(JsonFieldType.STRING).description("환자 등록 번호"),
					fieldWithPath("content[].genderCode").type(JsonFieldType.STRING).description("성별 코드"),
					fieldWithPath("content[].dateOfBirth").type(JsonFieldType.STRING).description("생년월일"),
					fieldWithPath("content[].mobilePhoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호"),
					fieldWithPath("content[].recentlyVisited").type(JsonFieldType.STRING).description("최근 방문 일자"),
					fieldWithPath("pageable").description("페이지 정보"),
					fieldWithPath("pageable.sort.empty").description("정렬 정보 - 비어 있음 여부"),
					fieldWithPath("pageable.sort.sorted").description("정렬 정보 - 정렬됨 여부"),
					fieldWithPath("pageable.sort.unsorted").description("정렬 정보 - 정렬되지 않음 여부"),
					fieldWithPath("pageable.offset").description("오프셋"),
					fieldWithPath("pageable.pageNumber").description("페이지 번호"),
					fieldWithPath("pageable.pageSize").description("페이지 크기"),
					fieldWithPath("pageable.paged").description("페이징 여부"),
					fieldWithPath("pageable.unpaged").description("페이징되지 않음 여부"),
					fieldWithPath("last").description("마지막 페이지 여부"),
					fieldWithPath("totalElements").description("전체 요소 수"),
					fieldWithPath("totalPages").description("전체 페이지 수"),
					fieldWithPath("size").description("현재 페이지의 요소 수"),
					fieldWithPath("number").description("현재 페이지 번호"),
					fieldWithPath("sort.empty").description("정렬 정보 - 비어 있음 여부"),
					fieldWithPath("sort.sorted").description("정렬 정보 - 정렬됨 여부"),
					fieldWithPath("sort.unsorted").description("정렬 정보 - 정렬되지 않음 여부"),
					fieldWithPath("first").description("첫 번째 페이지 여부"),
					fieldWithPath("numberOfElements").description("현재 페이지의 실제 요소 수"),
					fieldWithPath("empty").description("페이지의 내용이 비어 있는지 여부")
				)));
	}


	@Test
	public void testCreatePatient() throws Exception {
		Faker faker = new Faker(Locale.KOREA);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		PatientCreateRequestDto requestDto = PatientCreateRequestDto.builder()
			.patientName(faker.name().fullName())
			.hospitalId(1L)
			.genderCode(faker.options().option("M", "F"))
			.dateOfBirth(dateFormat.format(faker.date().birthday()))
			.mobilePhoneNumber(faker.phoneNumber().cellPhone())
			.build();

		mockMvc.perform(post("/api/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isCreated())
			.andDo(print())
			.andDo(document("create-patient",
				requestFields(
					fieldWithPath("patientName").description("환자이름"),
					fieldWithPath("hospitalId").description("병원 아이디"),
					fieldWithPath("genderCode").description("성별 코드"),
					fieldWithPath("dateOfBirth").description("출생일"),
					fieldWithPath("mobilePhoneNumber").description("모바일 전화번호")
				),
				responseFields(
					fieldWithPath("patientId").description("환자 아이디"),
					fieldWithPath("patientName").description("환자이름"),
					fieldWithPath("patientRegistrationNumber").description("환자 등록 번호"),
					fieldWithPath("genderCode").description("성별 코드"),
					fieldWithPath("dateOfBirth").description("출생일"),
					fieldWithPath("mobilePhoneNumber").description("모바일 전화번호")
				)));
	}

	@Test
	public void testUpdatePatient() throws Exception {
		Long patientId = 1L;
		PatientUpdateRequestDto requestDto = PatientUpdateRequestDto.builder()
			.patientName("수정이름")
			.genderCode("F")
			.dateOfBirth("2022-02-22")
			.mobilePhoneNumber("010-2222-2222")
			.build();

		mockMvc.perform(put("/api/patients/{patientId}", patientId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isOk())
			.andDo(document("update-patient",
				pathParameters(
					parameterWithName("patientId").description("수정할 환자 아이디")
				),
				requestFields(
					fieldWithPath("patientName").description("수정된 환자 이름"),
					fieldWithPath("genderCode").description("수정된 성별 코드"),
					fieldWithPath("dateOfBirth").description("수정된 생년월일"),
					fieldWithPath("mobilePhoneNumber").description("수정된 휴대폰 번호")
				),
				responseFields(
					fieldWithPath("patientId").description("환자 아이디"),
					fieldWithPath("patientName").description("환자이름"),
					fieldWithPath("patientRegistrationNumber").description("환자 등록 번호"),
					fieldWithPath("genderCode").description("성별 코드"),
					fieldWithPath("dateOfBirth").description("출생일"),
					fieldWithPath("mobilePhoneNumber").description("모바일 전화번호")
				)));
	}

	@Test
	public void testDeletePatient() throws Exception {
		Long patientId = 1L;

		mockMvc.perform(delete("/api/patients/{patientId}", patientId))
			.andExpect(status().isNoContent())
			.andDo(document("delete-patient",
				pathParameters(
					parameterWithName("patientId").description("삭제할 환자 아이디")
				)));
	}
}