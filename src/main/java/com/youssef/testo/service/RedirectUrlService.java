package com.youssef.testo.service;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.youssef.testo.entity.Url;

@Service
public class RedirectUrlService {

	public static final String URL_NOT_FOUND = "URL not found";

	private final UrlService urlService;

	public RedirectUrlService(UrlService urlService) {
		super();
		this.urlService = urlService;
	}

	public ResponseEntity<Object> redirect(String encodedUrl) {
		try {
			Url url = urlService.decodeAndGet(encodedUrl);
			
			if (url != null) {
				urlService.incrementAccessCnt(url);
			} else {
				throw new IllegalArgumentException(URL_NOT_FOUND);
			}
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getLongUrl())).build();
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
