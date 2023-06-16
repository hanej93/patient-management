package com.example.patientmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {

	@ModelAttribute("schTypes")
	public List<SchType> schTypes() {
		List<SchType> schTypes = new ArrayList<>();
		schTypes.add(new SchType("patientName", "이름"));
		schTypes.add(new SchType("patientRegistrationNumber", "환자등록번호"));
		schTypes.add(new SchType("dateOfBirth", "생년월일"));
		return schTypes;
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
