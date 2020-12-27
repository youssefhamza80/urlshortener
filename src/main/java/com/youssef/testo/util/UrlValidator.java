package com.youssef.testo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.youssef.testo.config.ShortUrlConfig;

@Component
public class UrlValidator {
	
	private final ShortUrlConfig configProperties;
	
	
	public UrlValidator(ShortUrlConfig configProperties) {
		super();
		this.configProperties = configProperties;
	}

	// Regex to check valid URL
	final String regex = "((http|https)://)?" + "[a-zA-Z0-9@:%._\\+~#?&//=]" + "{2,256}\\.[a-z]"
			+ "{2,6}\\b([-a-zA-Z0-9@:%" + "._\\+~#?&//=]*)";

	// Compile the ReGex
	final Pattern p = Pattern.compile(regex);

	public void validateUrl(String url) throws Exception{
		// If the string is empty
		// return false
		if (url == null) {
			throw new Exception("URL cannot be empty or null");
		}

		// Find match between given string
		// and regular expression
		// using Pattern.matcher()
		Matcher m = p.matcher(url);

		// Return if the string
		// matched the ReGex
		if (!m.matches()) {
			throw new Exception("URL is invalid");
		}
		
		if (url.startsWith(configProperties.getBaseUrl())) {
			throw new Exception("URL is already shortened by this service");
		}
	}
}
