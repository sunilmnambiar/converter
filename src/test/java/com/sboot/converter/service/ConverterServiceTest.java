package com.sboot.converter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.cache.LoadingCache;
import com.sboot.converter.dto.Currency;
import com.sboot.converter.dto.GeoCode;
import com.sboot.converter.exception.ApplicationException;

@SpringBootTest
class ConverterServiceTest {

	private LoadingCache<String, GeoCode> geoCache;
	private LoadingCache<Currency, BigDecimal> currencyCache;
	private ConverterService converterService;

	@BeforeEach
	@SuppressWarnings("unchecked")
	void setup() {
		geoCache = mock(LoadingCache.class);
		currencyCache = mock(LoadingCache.class);
		converterService = new ConverterService(geoCache, currencyCache);
	}

	@Test
	void testConvertCurrencyHappyPath() throws Exception {
		//prepare
		Currency currency = new Currency();
		currency.setSourceCurrency("eth");
		currency.setIpAddress("85.214.132.117");

		when(geoCache.get(any(String.class))).thenReturn(mock(GeoCode.class));
		when(currencyCache.get(any(Currency.class))).thenReturn(BigDecimal.TEN);

		//act
		converterService.convertCurrency(currency);
		
		//assert
		assertThat(currency.getTargetValue()).isEqualTo(BigDecimal.TEN);
		assertThat(currency.getTargetValueLocalized()).isNotNull();
	}

	@Test
	void testConvertCurrencyException() throws Exception {
		//prepare
		Currency currency = new Currency();
		currency.setSourceCurrency("eth");
		currency.setIpAddress("85.214.132.117");

		GeoCode geoCode=  new GeoCode();
		geoCode.setCountryCode("EUR");
		when(geoCache.get(any(String.class))).thenReturn(geoCode);
		when(currencyCache.get(any(Currency.class))).thenThrow(ApplicationException.class);

		//act and assert
		assertThrows(ApplicationException.class,
				() -> converterService.convertCurrency(currency),
				"Expected convertCurrency() to throw ApplicationException");
	}

}
