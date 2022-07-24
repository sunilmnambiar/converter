package com.sboot.converter.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sboot.converter.dao.Conversion;
import com.sboot.converter.dao.ConversionRepository;
import com.sboot.converter.dto.ConversionHistory;
import com.sboot.converter.dto.Currency;

@Service
public class ConversionHistoryService {
	
	private ConversionRepository conversionRepository;
	
	public List<ConversionHistory> getAllConversions(String userName) {
		List<ConversionHistory> conversions = new ArrayList<>();
		//TODO: find all by user
		conversionRepository.findAll().forEach(conversion -> conversions.add(buildConversionHistory(conversion)));
		return conversions;
	}
	
	public void addToHistory(Currency currency, String ueserName) {
		Conversion conversion = new Conversion();
		conversion.setUserName(ueserName);
		conversion.setSourceCurrency(currency.getSourceCurrency());
		conversion.setTargetCurrency(currency.getTargetCurrency());
		conversion.setTargetValue(currency.getTargetValue());
		conversion.setTargetValueLocalized(currency.getTargetValueLocalized());
		conversion.setDate(LocalDateTime.now());
		conversionRepository.save(conversion);
	}
	
	private ConversionHistory buildConversionHistory(Conversion conversion) {
		ConversionHistory conversionHistory = new ConversionHistory();
		conversionHistory.setSourceCurrency(conversion.getSourceCurrency());
		conversionHistory.setTargetCurrency(conversion.getTargetCurrency());
		conversionHistory.setTargetValue(conversion.getTargetValue());
		conversionHistory.setTargetValueLocalized(conversion.getTargetValueLocalized());
		conversionHistory.setDate(conversion.getDate());
		return conversionHistory;
	}

	@Autowired
	public ConversionHistoryService(ConversionRepository conversionRepository) {
		this.conversionRepository = conversionRepository;
	}

}
