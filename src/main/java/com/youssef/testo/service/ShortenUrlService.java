package com.youssef.testo.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlAppConfig;
import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.UrlOperation;
import com.youssef.testo.repository.UrlOperationRepository;
import com.youssef.testo.repository.UrlRepository;
import com.youssef.testo.util.Base62Encoder;
import com.youssef.testo.util.UrlValidation;

@Service
public class ShortenUrlService {

	private final Base62Encoder urlEncoder;

	private final UrlValidation urlValidation;

	private final UrlRepository urlRepository;

	private final UrlOperationRepository urlOperationRepository;

	private final ShortUrlAppConfig shortUrlConfig;

	public ShortenUrlService(UrlRepository urlDataRepository, UrlOperationRepository urlOperationRepository,
			ShortUrlAppConfig shortUrlConfig, UrlValidation urlValidator, Base62Encoder urlEncoder) {
		super();
		this.urlEncoder = urlEncoder;
		this.urlValidation = urlValidator;
		this.urlRepository = urlDataRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.shortUrlConfig = shortUrlConfig;
	}

	public ResponseEntity<Object> shortenUrl(String longUrl, String userName) {
		try {
			Optional<Url> shortendUrl = urlRepository.findByLongUrl(longUrl);
			Url url = null;
			if (shortendUrl.isPresent()) {
				url = shortendUrl.get();
			} else {
				urlValidation.validateUrl(longUrl);
				url = new Url(longUrl);
				urlRepository.save(url);
			}
			
			url.setShortUrl(String.format(shortUrlConfig.getBaseRedirectUrl(), urlEncoder.encode(url.getUrlId())));

			urlOperationRepository.save(new UrlOperation(url.getUrlId(), userName, Instant.now(),
					shortUrlConfig.getUrlOperationShortenUrl()));

			url.setAccessCnt(urlOperationRepository.countByUrlIdAndOperation(url.getUrlId(),
					shortUrlConfig.getUrlOperationAccessUrl()));

			url.setShortenCnt(urlOperationRepository.countByUrlIdAndOperation(url.getUrlId(),
					shortUrlConfig.getUrlOperationShortenUrl()));

			return new ResponseEntity<>(url, HttpStatus.CREATED);
		}

		catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
