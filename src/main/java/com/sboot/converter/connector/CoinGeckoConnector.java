package com.sboot.converter.connector;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sboot.converter.dto.Currency;
import com.sboot.converter.exception.ApplicationException;
import com.sboot.converter.util.CurrencyUtil;

@Component
public class CoinGeckoConnector implements CryptoConnector {

	private Logger logger = LoggerFactory.getLogger(CoinGeckoConnector.class);

	private RestTemplate restTemplate;
	private String cgUrl;

	/**
	 * Get conversion rate from CoinGecko
	 */
	@Override
	public BigDecimal getConversionRate(Currency currency) {
		String fromCurrency = currency.getSourceCurrency();
		String toCurrency = currency.getTargetCurrency();
		String fromCurrencyCode = CurrencyUtil.CG_CODE_MAP.get(fromCurrency.toLowerCase());

		ResponseEntity<String> response = restTemplate.getForEntity(cgUrl, String.class, fromCurrencyCode, toCurrency);
		if (response.getStatusCode() == HttpStatus.OK) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode responseMap = mapper.readTree(response.getBody());
				BigDecimal converted = responseMap.findValue(toCurrency.toLowerCase()).decimalValue();

				logger.info("Conversion completed, from={}, to={}, value={}", fromCurrency, toCurrency, converted);

				return converted;

			} catch (JsonMappingException e) {
				throw new ApplicationException("Geoplugin response parsing failed", e);
			} catch (JsonProcessingException e) {
				throw new ApplicationException("Geoplugin response parsing failed", e);
			}
		} else {
			throw new ApplicationException("Geoplugin service call failed, " + response.getBody());
		}
	}

	@Autowired
	public CoinGeckoConnector(RestTemplate restTemplate, @Value("${com.sboot.converter.connector.cg.url}") String cgUrl) {
		this.restTemplate = restTemplate;
		this.cgUrl = cgUrl;
	}
	
}
