package com.sboot.converter.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sboot.converter.dto.Currency;

@SpringBootTest
class ConverterServiceTest {
	
	ConverterService converterService = new ConverterService(null);

	@Test
	void testConvertCurrency() {
		Currency currency = new Currency();
		converterService.convertCurrency(currency);
		assertThat(currency.getTargetValueLocalized()).isNotNull();
	}

}
