package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.common.Utility.*;
import com.example.demo.common.ValidationException;

@RestController
@RequestMapping("/api")
public class ValidatorController {

	@Value("${comment.limit}")
	private int limit;
	
	@Value("${counth.max}")
	private int counthLimit;
	@Value("${countm.max}")
	private int countmLimit;
	@Value("${countl.max}")
	private int countlLimit;

	
	@RequestMapping(value = "/validate", consumes = MediaType.TEXT_PLAIN_VALUE,method = RequestMethod.POST)
    public String validateComment(@RequestBody String comment) throws ValidationException {
		if (comment.length() > limit)
			throw new ValidationException("Limit exceeded");
		String[] array= comment.split("(?=[,.])|\\s+");
		int counth=0,countm=0,countl=0;
		for(int j=0;j<array.length;j++) {
		for (int i = 0; i < validatorDictionaryHigh.length; i++) {
			if (array[i].equals(validatorDictionaryHigh[i]))
				counth++;
			
		}
		for (int i = 0; i < validatorDictionaryMid.length; i++) {
			if (array[i].equals(validatorDictionaryMid[i]))
				countm++;
			
		}
		for (int i = 0; i < validatorDictionaryLow.length; i++) {
			if (array[i].equals(validatorDictionaryLow[i]))
				countl++;
			
		}
		}
		int[] arr= {counth,countm,countl};
		return feedbackRule(arr).name();
		//return Feedback.Deliver;
	}
	
	private FEEDBACK feedbackRule(final int[] arr) {
		if(arr[RISK.HIGH.ordinal()]==0) {
			if(arr[RISK.MID.ordinal()]==0) {
				/*if(arr[RISK.LOW.ordinal()]==0) {
					return FEEDBACK.Deliver;
				}
				else */if(arr[RISK.LOW.ordinal()]<countlLimit) {
					return FEEDBACK.Deliver;
				}
				else return FEEDBACK.Modiify;
			}
			else if(arr[RISK.MID.ordinal()]<countmLimit) {
				return FEEDBACK.Modiify;
			}
			else return FEEDBACK.Quarantine;
		}
		else if(arr[RISK.HIGH.ordinal()]<counthLimit) {
			return FEEDBACK.Quarantine;
		}
		else return FEEDBACK.Reject;
	}
}
