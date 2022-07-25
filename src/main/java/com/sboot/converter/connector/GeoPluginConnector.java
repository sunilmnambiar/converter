package com.sboot.converter.connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sboot.converter.dto.GeoCode;
import com.sboot.converter.exception.ApplicationException;

@Component
public class GeoPluginConnector implements GeoConnector {

	private Logger logger = LoggerFactory.getLogger(GeoPluginConnector.class);

	private RestTemplate restTemplate;

	/**
	 * Get geocode from GeoPlugin
	 */
	@Override
	public GeoCode getGeoCode(String ipAddress) {
		ResponseEntity<String> response = restTemplate.getForEntity("http://www.geoplugin.net/json.gp?ip={ip}",
				String.class, ipAddress);
		if (response.getStatusCode() == HttpStatus.OK) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonNode responseMap = mapper.readTree(response.getBody());
				String countryCode = responseMap.findValue("geoplugin_countryCode").textValue();
				String currencyCode = responseMap.findValue("geoplugin_currencyCode").textValue();
				String currencySymbol = responseMap.findValue("geoplugin_currencySymbol_UTF8").textValue();
				logger.info("Response: {}, {}, {}", countryCode, currencyCode, currencySymbol);

				GeoCode geoCode = new GeoCode();
				geoCode.setCountryCode(countryCode);
				geoCode.setCurrencyCode(currencyCode);
				geoCode.setCurrencySymbol(currencySymbol);

				return geoCode;
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			throw new ApplicationException("Geoplugin response parsing failed");
		} else {
			throw new ApplicationException("Geoplugin service call failed");
		}
	}

	@Autowired
	public GeoPluginConnector(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
