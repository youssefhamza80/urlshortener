package com.youssef.testo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.testo.service.ShortenUrlService;
import com.youssef.testo.service.dto.ShortenUrlResult;

@RestController
@RequestMapping("/shortenurl")
public class ShortenUrlController {

	private final ShortenUrlService shortenUrlService;

	public ShortenUrlController(ShortenUrlService shortenUrlService) {
		super();
		this.shortenUrlService = shortenUrlService;
	}
	
	@PostMapping
	public ShortenUrlResult getShortendUrl(@RequestBody String url) {
		return shortenUrlService.shortenUrl(url, null);
	}	
}
