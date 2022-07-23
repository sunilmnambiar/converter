package com.sboot.converter.dto;

import java.math.BigDecimal;

public class Currency {
	
	private String sourceCurrency;
	private String ipAddress;
	private String targetCurrency;
	private BigDecimal targetValue;
	private String targetValueLocalized;
	
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
	
}
