package com.sboot.converter.dto;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

public class Currency {

	private String sourceCurrency;
	private String ipAddress;
	private Locale locale;
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

	@Override
	public int hashCode() {
		return Objects.hash(sourceCurrency, targetCurrency);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		return Objects.equals(sourceCurrency, other.sourceCurrency)
				&& Objects.equals(targetCurrency, other.targetCurrency);
	}

}
