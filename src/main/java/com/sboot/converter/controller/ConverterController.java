package com.sboot.converter.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.common.net.InetAddresses;
import com.sboot.converter.dto.Currency;
import com.sboot.converter.service.ConversionHistoryService;
import com.sboot.converter.service.ConverterService;
import com.sboot.converter.util.CurrencyUtil;

@Controller
public class ConverterController {
	
	Logger logger = LoggerFactory.getLogger(ConverterController.class);
	
	private ConverterService converterService;
	private ConversionHistoryService conversionHistoryService;
	
	@GetMapping("/")
	public String index(Model model, Principal principal) {
	    model.addAttribute("currencies", CurrencyUtil.CG_CODE_MAP.keySet());
	    model.addAttribute("currency", new Currency());
	    
	    model.addAttribute("conversionHistory", conversionHistoryService.getAllConversions(principal.getName()));
	    
		return "index";
	}
	
	@PostMapping("/")
	public String convert(@ModelAttribute Currency currency, Model model,
			HttpServletRequest request, Principal principal) throws Exception {
		logger.info("Request to convert currency: from={}, ip={}", 
				currency.getSourceCurrency(), currency.getIpAddress());
		String userName = principal.getName();
		if(StringUtils.hasText(currency.getSourceCurrency())) {
			if(!InetAddresses.isInetAddress(currency.getIpAddress())) {
		    	currency.setIpAddress(request.getRemoteAddr());
		    }
		    
		    logger.debug("IP Address is: {}", currency.getIpAddress());
		    
		    converterService.convertCurrency(currency);
		    conversionHistoryService.addToHistory(currency, userName);
		} else {
			model.addAttribute("error", "Please select at least one currency...");
		}
	    
	    model.addAttribute("currency", currency);
	    model.addAttribute("conversionHistory", conversionHistoryService.getAllConversions(userName));
	    model.addAttribute("currencies", CurrencyUtil.CG_CODE_MAP.keySet());
	    
		return "index";
	}
	
	@Autowired
    public ConverterController(ConverterService converterService, ConversionHistoryService conversionHistoryService) {
		this.converterService = converterService;
		this.conversionHistoryService = conversionHistoryService;
    }

}
