package com.sboot.converter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

public class ConversionHistory {

	private String userName;
	private String sourceCurrency;
	private String ipAddress;
	private Locale locale;
	private String targetCurrency;
	private BigDecimal targetValue;
	private String targetValueLocalized;
	private LocalDateTime date;

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public BigDecimal getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(BigDecimal targetValue) {
		this.targetValue = targetValue;
	}

	public String getTargetValueLocalized() {
		return targetValueLocalized;
	}

	public void setTargetValueLocalized(String targetValueLocalized) {
		this.targetValueLocalized = targetValueLocalized;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
