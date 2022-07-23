package com.sboot.converter.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sboot.converter.dto.Currency;
import com.sboot.converter.service.ConverterService;

@Controller
public class ConverterController {
	
	private ConverterService converterService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		List<String> options = new ArrayList<String>();
	    options.add("BTC");
	    options.add("ETH");
	    options.add("DOGE");
	    model.addAttribute("currencies", options);

	    model.addAttribute("currency", new Currency());
	    
		return "index";
	}
	
	@PostMapping("/")
	public String convert(@ModelAttribute Currency currency, Model model,
			HttpServletRequest request) {

		List<String> options = new ArrayList<String>();
	    options.add("BTC");
	    options.add("ETH");
	    options.add("DOGE");
	    model.addAttribute("currencies", options);
	    
	    converterService.convertCurrency(currency);
	    
	    model.addAttribute("currency", currency);
	    
		return "index";
	}
	
	@Autowired
    public ConverterController(ConverterService converterService) {
		this.converterService = converterService;
    }

}
