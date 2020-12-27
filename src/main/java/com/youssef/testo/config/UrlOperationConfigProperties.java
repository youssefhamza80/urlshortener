package com.youssef.testo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "urloperation")
public class UrlOperationConfigProperties {
	private String shortenUrl;
	private String accessUrl;

	public String getShortenUrl() {
		return shortenUrl;
	}

	public void setShortenUrl(String shortenUrl) {
		this.shortenUrl = shortenUrl;
	}

	public UrlOperationConfigProperties() {
		super();
	}

	public String getAccessUrl() {
		return accessUrl;
	}

	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
}