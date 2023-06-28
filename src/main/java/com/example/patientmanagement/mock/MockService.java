package com.example.patientmanagement.mock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.patientmanagement.dto.request.PatientCreateRequestDto;
import com.example.patientmanagement.dto.request.VisitCreateRequestDto;
import com.example.patientmanagement.dto.response.HospitalDto;
import com.example.patientmanagement.entity.Code;
import com.example.patientmanagement.entity.CodeGroup;
import com.example.patientmanagement.entity.Hospital;
import com.example.patientmanagement.repository.CodeGroupRepository;
import com.example.patientmanagement.repository.HospitalRepository;
import com.example.patientmanagement.service.PatientService;
import com.example.patientmanagement.service.VisitService;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@Profile({"!prod"})
public class MockService {

	private final CodeGroupRepository codeGroupRepository;
	private final HospitalRepository hospitalRepository;
	private final PatientService patientService;
	private final VisitService visitService;

	public void generateCodeGroup() {
		CodeGroup codeGroup1 = CodeGroup.builder()
			.codeGroupId("GC")
			.codeGroupName("성별코드")
			.description("성별을 표시")
			.build();

		CodeGroup codeGroup2 = CodeGroup.builder()
			.codeGroupId("VSC")
			.codeGroupName("방문상태코드")
			.description("환자방문의 상태(방문증, 종료, 취소)")
			.build();

		CodeGroup codeGroup3 = CodeGroup.builder()
			.codeGroupId("MSC")
			.codeGroupName("진료과목코드")
			.description("진료과목(내과, 안과 등)")
			.build();

		CodeGroup codeGroup4 = CodeGroup.builder()
			.codeGroupId("TTC")
			.codeGroupName("진료유형코드")
			.description("진료의 유형(약처방, 검사 등)")
			.build();

		codeGroupRepository.saveAll(Arrays.asList(codeGroup1, codeGroup2, codeGroup3, codeGroup4));
	}

	public void generateCode() {
		CodeGroup genderCode = codeGroupRepository.findById("GC")
			.orElseThrow(() -> new IllegalArgumentException());

		Code genderCode1 = Code.builder()
			.codeGroup(genderCode)
			.codeId("M")
			.codeName("남")
			.build();
		Code genderCode2 = Code.builder()
			.codeGroup(genderCode)
			.codeId("F")
			.codeName("여")
			.build();
		genderCode.addCode(genderCode1);
		genderCode.addCode(genderCode2);

		CodeGroup visitStateCode = codeGroupRepository.findById("VSC")
			.orElseThrow(() -> new IllegalArgumentException());
		Code visitStateCode1 = Code.builder()
			.codeGroup(visitStateCode)
			.codeId("1")
			.codeName("방문중")
			.build();
		Code visitStateCode2 = Code.builder()
			.codeGroup(visitStateCode)
			.codeId("2")
			.codeName("종료")
			.build();
		Code visitStateCode3 = Code.builder()
			.codeGroup(visitStateCode)
			.codeId("3")
			.codeName("취소")
			.build();
		visitStateCode.addCode(visitStateCode1);
		visitStateCode.addCode(visitStateCode2);
		visitStateCode.addCode(visitStateCode3);


		CodeGroup medicalSubjectCode = codeGroupRepository.findById("MSC")
			.orElseThrow(() -> new IllegalArgumentException());
		Code medicalSubjectCode1 = Code.builder()
			.codeGroup(medicalSubjectCode)
			.codeId("01")
			.codeName("내과")
			.build();
		Code medicalSubjectCode2 = Code.builder()
			.codeGroup(medicalSubjectCode)
			.codeId("02")
			.codeName("안과")
			.build();
		medicalSubjectCode.addCode(medicalSubjectCode1);
		medicalSubjectCode.addCode(medicalSubjectCode2);

		CodeGroup treatmentTypeCode = codeGroupRepository.findById("TTC")
			.orElseThrow(() -> new IllegalArgumentException());
		Code treatmentTypeCode1 = Code.builder()
			.codeGroup(treatmentTypeCode)
			.codeId("0")
			.codeName("약처방")
			.build();
		Code treatmentTypeCode2 = Code.builder()
			.codeGroup(treatmentTypeCode)
			.codeId("1")
			.codeName("검사")
			.build();

		treatmentTypeCode.addCode(treatmentTypeCode1);
		treatmentTypeCode.addCode(treatmentTypeCode2);
	}

	public void generateHospital() {
		Faker faker = new Faker(Locale.KOREA);

		for (int i = 0; i < 10; i++) {
			Hospital hospital = Hospital.builder()
				.hospitalName(faker.medical().hospitalName())
				.careInstitutionNumber(faker.numerify("##########"))
				.directorName(faker.name().fullName())
				.build();
			hospitalRepository.save(hospital);
		}
	}

	public void generatePatient() {
		Faker faker = new Faker(Locale.KOREA);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 0; i < 100; i++) {
			String patientName = faker.name().fullName();
			Long hospitalId= faker.random().nextInt(1, 10).longValue();
			String genderCode = faker.options().option("M", "F");
			String dateOfBirth = dateFormat.format(faker.date().birthday());
			String mobilePhoneNumber = faker.phoneNumber().cellPhone();

			PatientCreateRequestDto requestDto = PatientCreateRequestDto.builder()
				.patientName(patientName)
				.hospitalId(hospitalId)
				.genderCode(genderCode)
				.dateOfBirth(dateOfBirth)
				.mobilePhoneNumber(mobilePhoneNumber)
				.build();
			patientService.createPatient(requestDto);
		}
	}

	public void generateVisit() {
		Faker faker = new Faker(Locale.KOREA);

		for (int i = 0; i < 150; i++) {
			Long patientId = faker.random().nextInt(1, 100).longValue();
			HospitalDto hospital = patientService.getPatientById(patientId).getHospital();
			LocalDateTime visitDate = LocalDateTime.ofInstant(faker.date().future(365, TimeUnit.DAYS).toInstant(),
				ZoneId.systemDefault());
			String visitStatusCode = faker.options().option("1", "2", "3");

			VisitCreateRequestDto requestDto = VisitCreateRequestDto.builder()
				.hospitalId(hospital.getHospitalId())
				.patientId(patientId)
				.visitDate(visitDate)
				.visitStatusCode(visitStatusCode)
				.build();
			visitService.createVisit(requestDto);
		}
	}

}
