package com.example.patientmanagement.utils;

import java.util.Random;

public class PatientUtils {

	public static String generatePatientRegistrationNumber(Long hospitalId, int digit) {
		Random random = new Random();
		long bound = (long) Math.pow(10, digit);
		long patientNumber = random.nextLong(1, bound);

		String hospitalCode = convertToAlphabet(hospitalId);
		String patientRegistrationNumber = hospitalCode + String.format("%06d", patientNumber);

		return patientRegistrationNumber;
	}

	private static String convertToAlphabet(long num) {
		StringBuilder sb = new StringBuilder();
		while (num > 0) {
			num--;
			char ch = (char) (num % 26 + 'A');
			num /= 26;
			sb.append(ch);
		}
		return sb.reverse().toString();
	}

}
