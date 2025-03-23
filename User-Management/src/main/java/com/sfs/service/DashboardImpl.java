package com.sfs.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sfs.dto.QuoteApiResponseDTO;
@Service
public class DashboardImpl implements DashboardService {
	
	private String quoteApiURL = "https://dummyjson.com/quotes/random";

	@Override
	public QuoteApiResponseDTO getQuote() {		
		RestTemplate rt = new RestTemplate();		
		ResponseEntity<QuoteApiResponseDTO> res = rt.getForEntity(quoteApiURL, QuoteApiResponseDTO.class);		
		QuoteApiResponseDTO response = res.getBody();		
		return response;
	}

}
