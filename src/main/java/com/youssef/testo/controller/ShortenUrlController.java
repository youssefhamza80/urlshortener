package com.youssef.testo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.testo.config.ShortUrlConfig;
import com.youssef.testo.service.ShortenUrlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Shorten URL Controller APIs", tags = { "Shorten URL Controller" })
@RestController
@RequestMapping("/shortenurl")
@PreAuthorize("permitAll()")
public class ShortenUrlController {

	private final ShortenUrlService shortenUrlService;

	public ShortenUrlController(ShortenUrlService shortenUrlService) {
		super();
		this.shortenUrlService = shortenUrlService;
	}

	@ApiOperation(value = "Shorten URL")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 401, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping
	public ResponseEntity<Object> getShortendUrl(@RequestBody String url) {
		return shortenUrlService.shortenUrl(url, "public");
	}
}
