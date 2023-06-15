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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.PatientUpdateRequestDto;
import com.example.patientmanagement.entity.Hospital;
import com.example.patientmanagement.entity.Patient;
import com.example.patientmanagement.repository.HospitalRepository;
import com.example.patientmanagement.repository.PatientRepository;
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

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private HospitalRepository hospitalRepository;

	private final Faker faker = new Faker(Locale.KOREA);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private Patient patient;
	private Hospital hospital;

	@BeforeEach
	public void setup() {
		patientRepository.deleteAll();
		hospitalRepository.deleteAll();

		hospital = createSampleHospital();
		patient = createSamplePatient(hospital);
		hospitalRepository.save(hospital);
		patientRepository.save(patient);
	}

	private Hospital createSampleHospital() {
		Hospital hospital = Hospital.builder()
			.hospitalName(faker.medical().hospitalName())
			.careInstitutionNumber(faker.numerify("######"))
			.directorName(faker.name().fullName())
			.build();
		return hospital;
	}

	private Patient createSamplePatient(Hospital hospital) {
		Patient patient = Patient.builder()
			.hospital(hospital)
			.patientName(faker.name().fullName())
			.patientRegistrationNumber(faker.numerify("########"))
			.genderCode(faker.options().option("M", "F"))
			.dateOfBirth(dateFormat.format(faker.date().birthday()))
			.mobilePhoneNumber(faker.phoneNumber().phoneNumber())
			.build();
		return patient;
	}

	@Test
	@DisplayName("환자 아이디로 조회")
	public void getPatientById() throws Exception {
		Long patientId = patient.getPatientId();

		mockMvc.perform(get("/api/patients/{patientId}", patientId))
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
					fieldWithPath("visits").description("방문 리스트").type(JsonFieldType.ARRAY).optional(),
					fieldWithPath("visits[].visitId").description("방문 ID").type(JsonFieldType.NUMBER).optional(),
					fieldWithPath("visits[].hospitalId").description("병원 ID").type(JsonFieldType.NUMBER).optional(),
					fieldWithPath("visits[].patientId").description("환자 ID").type(JsonFieldType.NUMBER).optional(),
					fieldWithPath("visits[].visitDate").description("방문 날짜").type(JsonFieldType.STRING).optional(),
					fieldWithPath("visits[].visitStatusCode").description("방문 상태 코드").type(JsonFieldType.STRING).optional()
				)));
	}

	@Test
	@DisplayName("모든 환자 조회")
	public void getAllPatients() throws Exception {
		mockMvc.perform(get("/api/patients/all"))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("get-all-patients",
				responseFields(
					fieldWithPath("[]").description("모든 환자 리스트").type(JsonFieldType.ARRAY),
					fieldWithPath("[].patientId").description("환자 아이디").type(JsonFieldType.NUMBER),
					fieldWithPath("[].patientName").description("환자 이름").type(JsonFieldType.STRING),
					fieldWithPath("[].patientRegistrationNumber").description("환자 등록 번호").type(JsonFieldType.STRING),
					fieldWithPath("[].genderCode").description("성별 코드").type(JsonFieldType.STRING),
					fieldWithPath("[].dateOfBirth").description("출생일").type(JsonFieldType.STRING),
					fieldWithPath("[].mobilePhoneNumber").description("모바일 전화번호").type(JsonFieldType.STRING)
				)));
	}

	@Test
	@DisplayName("페이징된 환자 조회")
	public void getPagedPatients() throws Exception {

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
					fieldWithPath("content[]").description("페이징된 환자 리스트").type(JsonFieldType.ARRAY).optional(),
					fieldWithPath("content[].patientId").description("환자 ID").type(JsonFieldType.NUMBER).optional(),
					fieldWithPath("content[].patientName").description("환자 이름").type(JsonFieldType.STRING).optional(),
					fieldWithPath("content[].patientRegistrationNumber").description("환자 등록 번호").type(JsonFieldType.STRING).optional(),
					fieldWithPath("content[].genderCode").description("성별 코드").type(JsonFieldType.STRING).optional(),
					fieldWithPath("content[].dateOfBirth").description("생년월일").type(JsonFieldType.STRING).optional(),
					fieldWithPath("content[].mobilePhoneNumber").description("휴대폰 번호").type(JsonFieldType.STRING).optional(),
					fieldWithPath("content[].recentlyVisited").description("최근 방문 일자").type(JsonFieldType.STRING).optional(),
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
	@DisplayName("환자 생성")
	public void createPatient() throws Exception {
		Faker faker = new Faker(Locale.KOREA);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		PatientCreateRequestDto requestDto = PatientCreateRequestDto.builder()
			.patientName(faker.name().fullName())
			.hospitalId(hospital.getHospitalId())
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
					fieldWithPath("patientName").description("환자이름").type(JsonFieldType.STRING).attributes(key("optional").value(false)),
					fieldWithPath("hospitalId").description("병원 아이디").type(JsonFieldType.NUMBER).attributes(key("optional").value(false)),
					fieldWithPath("genderCode").description("성별 코드").type(JsonFieldType.STRING).attributes(key("optional").value(false)),
					fieldWithPath("dateOfBirth").description("출생일").type(JsonFieldType.STRING).attributes(key("optional").value(false)),
					fieldWithPath("mobilePhoneNumber").description("모바일 전화번호").type(JsonFieldType.STRING).attributes(key("optional").value(false))
				),
				responseFields(
					fieldWithPath("patientId").description("환자 아이디").type(JsonFieldType.NUMBER).attributes(key("optional").value(false)),
					fieldWithPath("patientName").description("환자이름").type(JsonFieldType.STRING).attributes(key("optional").value(false)),
					fieldWithPath("patientRegistrationNumber").description("환자 등록 번호").type(JsonFieldType.STRING).attributes(key("optional").value(false)),
					fieldWithPath("genderCode").description("성별 코드").type(JsonFieldType.STRING).attributes(key("optional").value(false)),
					fieldWithPath("dateOfBirth").description("출생일").type(JsonFieldType.STRING).attributes(key("optional").value(false)),
					fieldWithPath("mobilePhoneNumber").description("모바일 전화번호").type(JsonFieldType.STRING).attributes(key("optional").value(false))
				)));
	}

	@Test
	@DisplayName("환자 수정")
	public void updatePatient() throws Exception {
		Long patientId = patient.getPatientId();
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
					fieldWithPath("patientName").description("수정된 환자 이름").type(JsonFieldType.STRING).attributes(key("optional").value(true)),
					fieldWithPath("genderCode").description("수정된 성별 코드").type(JsonFieldType.STRING).attributes(key("optional").value(true)),
					fieldWithPath("dateOfBirth").description("수정된 생년월일").type(JsonFieldType.STRING).attributes(key("optional").value(true)),
					fieldWithPath("mobilePhoneNumber").description("수정된 휴대폰 번호").type(JsonFieldType.STRING).attributes(key("optional").value(true))
				),
				responseFields(
					fieldWithPath("patientId").description("환자 아이디").type(JsonFieldType.NUMBER),
					fieldWithPath("patientName").description("환자이름").type(JsonFieldType.STRING).optional(),
					fieldWithPath("patientRegistrationNumber").description("환자 등록 번호").type(JsonFieldType.STRING).optional(),
					fieldWithPath("genderCode").description("성별 코드").type(JsonFieldType.STRING).optional(),
					fieldWithPath("dateOfBirth").description("출생일").type(JsonFieldType.STRING).optional(),
					fieldWithPath("mobilePhoneNumber").description("모바일 전화번호").type(JsonFieldType.STRING).optional()
				)));
	}

	@Test
	@DisplayName("환자 삭제")
	public void deletePatient() throws Exception {
		Long patientId = patient.getPatientId();

		mockMvc.perform(delete("/api/patients/{patientId}", patientId))
			.andExpect(status().isNoContent())
			.andDo(document("delete-patient",
				pathParameters(
					parameterWithName("patientId").description("삭제할 환자 아이디")
				)));
	}
}