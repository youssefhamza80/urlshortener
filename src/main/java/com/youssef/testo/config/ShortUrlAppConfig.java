package com.youssef.testo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shorturl")
public class ShortUrlAppConfig {
	
	private String publicUserName;
	
	private String baseRedirectUrl;
	
	private String[] urlSchemes;
	
	private String urlOperationShortenUrl;
	private String urlOperationAccessUrl;
		
	public ShortUrlAppConfig() {
		super();		
	}

	public String getPublicUserName() {
		return publicUserName;
	}

	public void setPublicUserName(String publicUserName) {
		this.publicUserName = publicUserName;
	}

	public String getBaseRedirectUrl() {
		return baseRedirectUrl;
	}

	public void setBaseRedirectUrl(String baseUrl) {
		this.baseRedirectUrl = baseUrl;
	}

	public String[] getUrlSchemes() {
		return urlSchemes;
	}

	public void setUrlSchemes(String[] urlSchemes) {
		this.urlSchemes = urlSchemes;
	}

	public String getUrlOperationShortenUrl() {
		return urlOperationShortenUrl;
	}

	public void setUrlOperationShortenUrl(String urlOperationShortenUrl) {
		this.urlOperationShortenUrl = urlOperationShortenUrl;
	}

	public String getUrlOperationAccessUrl() {
		return urlOperationAccessUrl;
	}

	public void setUrlOperationAccessUrl(String urlOperationAccessUrl) {
		this.urlOperationAccessUrl = urlOperationAccessUrl;
	}
}


