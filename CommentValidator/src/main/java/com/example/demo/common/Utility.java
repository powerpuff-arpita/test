package com.example.demo.common;

import org.springframework.beans.factory.annotation.Value;

public class Utility {
	public static String[] validatorDictionaryHigh = { "confidential", "confidential1", "confidential2" };
	public static String[] validatorDictionaryMid = { "confidential3", "confidential4", "confidential5" };
	public static String[] validatorDictionaryLow = { "confidential7", "confidential8", "confidential9" };

	public static enum FEEDBACK {
		Reject, Quarantine, Deliver, Modiify
	}

	public static enum RISK {
		HIGH, MID, LOW
	}

	}
