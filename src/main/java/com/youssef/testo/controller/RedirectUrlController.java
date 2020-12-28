package com.youssef.testo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.testo.service.RedirectUrlService;

@RestController
@RequestMapping("/r")
public class RedirectUrlController {

	private final RedirectUrlService rediectUrlService;

	public RedirectUrlController(RedirectUrlService rediectUrlService) {
		super();
		this.rediectUrlService = rediectUrlService;
	}

	@GetMapping("{encodedUrl}")
	public ResponseEntity<String> getAndRedirect(@PathVariable String encodedUrl) {
		return rediectUrlService.redirect(encodedUrl, null);
	}
}
