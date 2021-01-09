package com.youssef.testo.util;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlConfig;

@Service
public class UrlValidation {
	
	private final ShortUrlConfig shortUrlConfig;
	
	UrlValidator validator;
	
	public UrlValidation(ShortUrlConfig shortUrlConfig) {
		super();
		 
		this.shortUrlConfig = shortUrlConfig;
		validator = new UrlValidator(shortUrlConfig.getUrlSchemes());
	}

	public void validateUrl(String url) throws IllegalArgumentException{

		if (url == null) {
			throw new IllegalArgumentException("URL cannot be empty or null");
		}

		if (!validator.isValid(url)) {
			throw new IllegalArgumentException("URL is invalid");
		}
		
		if (url.startsWith(shortUrlConfig.getBaseRedirectUrl())) {
			throw new IllegalArgumentException("URL is already shortened by this service");
		}
	}
}
