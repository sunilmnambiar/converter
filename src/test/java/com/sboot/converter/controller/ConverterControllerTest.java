package com.sboot.converter.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import com.sboot.converter.dto.Currency;
import com.sboot.converter.exception.ApplicationException;
import com.sboot.converter.service.ConverterService;

@SpringBootTest
class ConverterControllerTest {

	private ConverterService converterService;
	private ConverterController converterController;

	@BeforeEach
	void setup() {
		converterService = mock(ConverterService.class);
		converterController = new ConverterController(converterService);
	}

	@Test
	void testConvertHappyPath() throws Exception {
		//arrange
		Currency currency = new Currency();
		currency.setSourceCurrency("eth");
		currency.setIpAddress("");

		doAnswer(invocation -> {
			Currency tempCurrency = (Currency) invocation.getArgument(0);
			tempCurrency.setTargetValue(BigDecimal.TEN);
			tempCurrency.setTargetValueLocalized("10.00 €");
			return null;
		}).when(converterService).convertCurrency(any(Currency.class));

		//act
		String template = converterController.convert(currency, mock(Model.class), mock(HttpServletRequest.class));

		//assert
		assertThat(currency.getTargetValue()).isEqualTo(BigDecimal.TEN);
		assertThat(currency.getTargetValueLocalized()).isNotNull();
		assertThat(template).isEqualTo("index");
	}

	@Test
	void testConvertException() throws Exception {
		//arrange
		Currency currency = new Currency();
		currency.setSourceCurrency("eth");
		currency.setIpAddress("");

		doAnswer(invocation -> {
			throw new ApplicationException("Conversion failed");
		}).when(converterService).convertCurrency(any(Currency.class));

		//act and assert
		assertThrows(ApplicationException.class,
				() -> converterController.convert(currency, mock(Model.class), mock(HttpServletRequest.class)),
				"Expected convert() to throw ApplicationException");
	}

}
