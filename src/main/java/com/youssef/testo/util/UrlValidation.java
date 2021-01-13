package com.youssef.testo.util;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlAppConfig;

@Service
public class UrlValidation {

	public static final String INVALID_URL = "URL is invalid";

	public static final String URL_ALREADY_SHORTENED = "URL is already shortened by this service";

	public static final String EMPTY_URL = "URL cannot be empty or null";

	private final ShortUrlAppConfig shortUrlConfig;

	UrlValidator validator;

	public UrlValidation(ShortUrlAppConfig shortUrlConfig) {
		super();

		this.shortUrlConfig = shortUrlConfig;
		validator = new UrlValidator(shortUrlConfig.getUrlSchemes());
	}

	public void validateUrl(String url) throws IllegalArgumentException {

		if (url == null || url.equals("")) {
			throw new IllegalArgumentException(EMPTY_URL);
		}

		if (url.startsWith(shortUrlConfig.getBaseRedirectUrl())) {
			throw new IllegalArgumentException(URL_ALREADY_SHORTENED);
		}
		
		if (!validator.isValid(url)) {
			throw new IllegalArgumentException(INVALID_URL);
		}
	}
}
