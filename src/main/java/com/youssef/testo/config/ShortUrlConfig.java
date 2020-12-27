package com.youssef.testo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shorturl")
public class ShortUrlConfig {
	private String baseUrl;
	
	private String successResult;
	
	public String getSuccessResult() {
		return successResult;
	}

	public void setSuccessResult(String successResult) {
		this.successResult = successResult;
	}

	public String getErrorResult() {
		return errorResult;
	}

	public void setErrorResult(String errorResult) {
		this.errorResult = errorResult;
	}

	private String errorResult;
	
	
	public ShortUrlConfig() {
		super();		
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}


