package com.example.patientmanagement.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.patientmanagement.entity.Code;
import com.example.patientmanagement.entity.CodeGroup;
import com.example.patientmanagement.entity.Hospital;
import com.example.patientmanagement.repository.CodeGroupRepository;
import com.example.patientmanagement.repository.HospitalRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {

	private final CodeGroupRepository codeGroupRepository;
	private final HospitalRepository hospitalRepository;

	@ModelAttribute("schTypes")
	public List<SchType> schTypes() {
		List<SchType> schTypes = new ArrayList<>();
		schTypes.add(new SchType("patientName", "이름"));
		schTypes.add(new SchType("patientRegistrationNumber", "환자등록번호"));
		schTypes.add(new SchType("dateOfBirth", "생년월일"));
		return schTypes;
	}

	@ModelAttribute("genderCodes")
	public List<Code> genderCodes() {
		CodeGroup codeGroup = codeGroupRepository.findById("GC").orElseThrow(() -> new IllegalArgumentException());
		return codeGroup.getCodes().stream().sorted(Comparator.comparing(Code::getCodeName)).toList();
	}

	@ModelAttribute("hospitals")
	public List<Hospital> hospitals() {
		return hospitalRepository.findAll(Sort.by("hospitalName"));
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@Data
	@AllArgsConstructor
	private static class SchType {
		private String value;
		private String name;
	}

}
