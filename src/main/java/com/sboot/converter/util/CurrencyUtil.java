package com.sboot.converter.util;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.sboot.converter.dto.GeoCode;

public class CurrencyUtil {
	
	private static Logger logger = LoggerFactory.getLogger(CurrencyUtil.class);

	// TODO: Retrieve from https://docs.google.com/spreadsheets/d/1wTTuxXt8n9q7C4NDXqQpI3wpKu1_5bGVmP9Xz0XGSyU/edit#gid=0 
	// and put it in a database table
	/**
	 * Get top 10 currency code mapping for CoinGecko
	 */
	public static Map<String, String> CG_CODE_MAP = Map.of("btc", "bitcoin", "eth", "ethereum", "usdt", "tether",
			"usdc", "usd-coin", "bnb", "binancecoin", "busd", "binance-usd", "xrp", "ripple", "ada", "cardano", "sol",
			"solana", "doge", "dogecoin");

	/**
	 * Returns true for local or null ip address
	 * 
	 * @param ipAddress
	 * @return boolean
	 */
	public static boolean isLocalIpAddress(String ipAddress) {
		return !StringUtils.hasText(ipAddress) || ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.0.1")
				|| ipAddress.equals("localhost");
	}

	/**
	 * Get GeoCode for default locale
	 * @return GeoCode
	 */
	public static GeoCode getDefaultGeoCode() {
		GeoCode geoCode;
		Locale defaultLocale = Locale.getDefault();
		logger.debug("{}, {}, {}", defaultLocale, defaultLocale.getCountry(), new Locale("", defaultLocale.getCountry()));
		geoCode = new GeoCode();
		geoCode.setCountryCode(defaultLocale.getCountry());
		geoCode.setCurrencyCode(
				java.util.Currency.getInstance(new Locale("", defaultLocale.getCountry())).getCurrencyCode());
		return geoCode;
	}

}
