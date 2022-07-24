package com.sboot.converter.connector;

import com.sboot.converter.dto.GeoCode;

public interface GeoConnector {

	GeoCode getGeoCode(String ipAddress);

}
