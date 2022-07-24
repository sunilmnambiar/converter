package com.sboot.converter.connector;

import java.math.BigDecimal;

import com.sboot.converter.dto.Currency;

public interface CryptoConnector {
	
	public BigDecimal getConversionRate(Currency currency);


}
