package com.sboot.converter.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CurrencyUtilTest {
	
	@Test
	public void testIsLocalIpAddress() {
		assertThat(CurrencyUtil.isLocalIpAddress(null)).isTrue();
		assertThat(CurrencyUtil.isLocalIpAddress("")).isTrue();
		assertThat(CurrencyUtil.isLocalIpAddress("0:0:0:0:0:0:0:1")).isTrue();
		assertThat(CurrencyUtil.isLocalIpAddress("127.0.0.1")).isTrue();
		assertThat(CurrencyUtil.isLocalIpAddress("localhost")).isTrue();
	}
	
	@Test
	public void testGetDefaultGeoCode() {
		assertThat(CurrencyUtil.getDefaultGeoCode()).isNotNull();
	}

}
