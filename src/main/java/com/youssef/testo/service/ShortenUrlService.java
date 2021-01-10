package com.youssef.testo.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.youssef.testo.entity.Url;

@Service
public class ShortenUrlService {

	private final UrlService urlService;

	public ShortenUrlService(UrlService urlService) {
		super();
		this.urlService = urlService;
	}

	public ResponseEntity<Object> shortenUrl(String longUrl, String userName) {
		try {
			Optional<Url> shortendUrl = urlService.findByLongUrl(longUrl);
			Url url = null;
			if (shortendUrl.isPresent()) {
				url = shortendUrl.get();
			} else {
				urlService.validateUrl(longUrl);
				url = new Url(longUrl);
				urlService.insertNewUrl(url);
			}

			urlService.incrementShortenCnt(url, userName);
			urlService.populateUrlAttributes(url);
			return new ResponseEntity<>(url, HttpStatus.CREATED);
		}

		catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
