package com.youssef.testo.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.testo.entity.Url;
import com.youssef.testo.service.RedirectUrlService;

@RestController
@RequestMapping("/r")
public class RedirectUrlController {

	private final RedirectUrlService rediectUrlService;

	public RedirectUrlController(RedirectUrlService rediectUrlService) {
		super();
		this.rediectUrlService = rediectUrlService;
	}

	@GetMapping("{id}")
	public ResponseEntity<String> getAndRedirect(@PathVariable long id) {
		try {
			Url urlData = rediectUrlService.getUrlAndIncrementAccessCnt(id, null);

			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlData.getLongUrl())).build();
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
