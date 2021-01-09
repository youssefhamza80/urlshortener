package com.youssef.testo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.testo.service.ShortenUrlService;

@RestController
@RequestMapping("/shortenurl")
@PreAuthorize("permitAll()")
public class ShortenUrlController {

	private final ShortenUrlService shortenUrlService;

	public ShortenUrlController(ShortenUrlService shortenUrlService) {
		super();
		this.shortenUrlService = shortenUrlService;	
	}

	@PostMapping
	public ResponseEntity<Object> getShortendUrl(@RequestBody String url) {
		return shortenUrlService.shortenUrl(url, null);
	}
}
