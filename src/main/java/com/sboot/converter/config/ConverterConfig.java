package com.sboot.converter.config;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sboot.converter.connector.CryptoConnector;
import com.sboot.converter.connector.GeoConnector;
import com.sboot.converter.dto.Currency;
import com.sboot.converter.dto.GeoCode;

@Configuration
public class ConverterConfig {
	
	private Logger logger = LoggerFactory.getLogger(ConverterConfig.class);

	/**
	 * Rest template for external calls
	 * @param restTemplateBuilder
	 * @return RestTemplate
	 */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
    
    /**
     * Cache for GeoCode
     * @param geoConnector
     * @return LoadingCache
     */
    @Bean
    public LoadingCache<String, GeoCode> geoCache(GeoConnector geoConnector) {
    	LoadingCache<String, GeoCode> cache = CacheBuilder.newBuilder()
    			.maximumSize(10000)
    			.expireAfterWrite(12, TimeUnit.HOURS)
    			.build(new CacheLoader<String, GeoCode>() {
            @Override
            public GeoCode load(String key) {
            	logger.info("GEOCACHE:: CACHE_MISS:: {}", key);
                return geoConnector.getGeoCode(key);
            }
        });
    	
    	return cache;
    }
    
    /**
     * Cache for Currency
     * @param cryptoConnector
     * @return LoadingCache
     */
	@Bean
    public LoadingCache<Currency, BigDecimal> currencyCache(CryptoConnector cryptoConnector) {
    	LoadingCache<Currency, BigDecimal> cache = CacheBuilder.newBuilder()
    			.maximumSize(10000)
    			.expireAfterWrite(5, TimeUnit.MINUTES)
    			.build(new CacheLoader<Currency, BigDecimal>() {
            @Override
            public BigDecimal load(Currency currency) {
            	logger.info("CURRENCYCACHE:: CACHE_MISS:: {}", currency.getSourceCurrency()+"_"+currency.getTargetCurrency());
                return cryptoConnector.getConversionRate(currency);
            }
        });
    	
    	return cache;
    }

}
