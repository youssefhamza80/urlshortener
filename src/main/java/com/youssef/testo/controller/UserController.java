package com.youssef.testo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.testo.service.RedirectUrlService;
import com.youssef.testo.service.ShortenUrlService;
import com.youssef.testo.service.dto.ShortenUrlResult;

@RestController
@RequestMapping("/user")
public class UserController {

	private final ShortenUrlService shortenUrlService;
	private final RedirectUrlService rediectUrlService;

	public UserController(ShortenUrlService shortenUrlService, RedirectUrlService rediectUrlService) {
		super();
		this.shortenUrlService = shortenUrlService;
		this.rediectUrlService = rediectUrlService;
	}

	@PostMapping("/{userName}/shortenurl")
	public ResponseEntity<ShortenUrlResult> getShortendUrl(@PathVariable String userName, @RequestBody String url) {
		return shortenUrlService.shortenUrl(url, userName);
	}

	@GetMapping("/{userName}/r/{encodedUrl}")
	public ResponseEntity<String> getAndRedirect(@PathVariable String userName, @PathVariable String encodedUrl) {
		return rediectUrlService.redirect(encodedUrl, userName);
	}
}
