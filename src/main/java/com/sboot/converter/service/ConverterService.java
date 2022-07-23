package com.sboot.converter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sboot.converter.dto.Currency;

@Service
public class ConverterService {
	
	//private RestTemplate restTemplate;

	public void convertCurrency(Currency currency) {
		currency.setTargetValueLocalized("2000");
	}
	
	@Autowired
	public ConverterService(RestTemplate restTemplate) {
		//this.restTemplate = restTemplate;
	}
	

}
