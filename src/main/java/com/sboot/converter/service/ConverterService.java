package com.sboot.converter.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.google.common.cache.LoadingCache;
import com.sboot.converter.dto.Currency;
import com.sboot.converter.dto.GeoCode;
import com.sboot.converter.util.CurrencyUtil;

@Service
public class ConverterService {

	private Logger logger = LoggerFactory.getLogger(ConverterService.class);

	private LoadingCache<String, GeoCode> geoCache;

	private LoadingCache<Currency, BigDecimal> currencyCache;

	/**
	 * Converts currency to user default currency
	 * @param currency
	 * @throws RestClientException
	 * @throws ExecutionException
	 */
	public void convertCurrency(Currency currency) throws RestClientException, ExecutionException {
		logger.debug("Initiating geo identification, ip_address={}", currency.getIpAddress());
		GeoCode geoCode;
		if (CurrencyUtil.isLocalIpAddress(currency.getIpAddress())) {
			geoCode = CurrencyUtil.getDefaultGeoCode();
			geoCode.setIpAddress(currency.getIpAddress());
		} else {
			geoCode = geoCache.get(currency.getIpAddress());
		}

		currency.setTargetCurrency(geoCode.getCurrencyCode());

		logger.debug("Initiating currency conversion, from={}, to={}", currency.getSourceCurrency(), 
				currency.getTargetCurrency());
		BigDecimal converted = currencyCache.get(currency);
		currency.setTargetValue(converted);

		Locale[] all = Locale.getAvailableLocales();
		for (Locale locale : all) {
			String country = locale.getCountry();
			if (country.equalsIgnoreCase(geoCode.getCountryCode())) {
				currency.setLocale(locale);
				break;
			}
		}

		logger.debug("Localizing converted value, from={}, to={}, value={}", currency.getSourceCurrency(), 
				currency.getTargetCurrency(), currency.getTargetValue());
		String localizedValue = NumberFormat.getCurrencyInstance(currency.getLocale()).format(currency.getTargetValue());
		currency.setTargetValueLocalized(localizedValue);
	}

	@Autowired
	public ConverterService(LoadingCache<String, GeoCode> geoCache, LoadingCache<Currency, BigDecimal> currencyCache) {
		this.geoCache = geoCache;
		this.currencyCache = currencyCache;
	}

}
