package com.youssef.testo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.testo.service.RedirectUrlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Redirection Controller APIs", tags = { "Redirection Controller" })
@RestController
@RequestMapping("/r")
public class RedirectUrlController {

	private final RedirectUrlService rediectUrlService;

	public RedirectUrlController(RedirectUrlService rediectUrlService) {
		super();
		this.rediectUrlService = rediectUrlService;
	}

	@ApiOperation(value = "Redirect short URL")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "URL Not Found") })
	@GetMapping("{encodedUrl}")
	public ResponseEntity<Object> getAndRedirect(@PathVariable String encodedUrl) {
		return rediectUrlService.redirect(encodedUrl);
	}
}
