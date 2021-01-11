package com.youssef.testo.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlAppConfig;
import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.UrlOperation;
import com.youssef.testo.repository.UrlOperationRepository;
import com.youssef.testo.repository.UrlRepository;
import com.youssef.testo.util.Base62Encoder;
import com.youssef.testo.util.UrlValidation;

@Service
public class UrlService {

	private final ShortUrlAppConfig config;

	private final UrlRepository urlRepository;

	private final UrlOperationRepository urlOperationRepository;

	private final Base62Encoder urlEncoder;

	private final UrlValidation urlValidation;

	public UrlService(UrlOperationRepository urlOperationRepository, Base62Encoder urlEncoder, ShortUrlAppConfig config,
			UrlRepository urlRepository, UrlValidation urlValidation) {
		this.config = config;
		this.urlRepository = urlRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.urlEncoder = urlEncoder;
		this.urlValidation = urlValidation;
	}

	public void populateUrlAttributes(Url url) {
		url.setShortUrl(String.format(config.getBaseRedirectUrl(), urlEncoder.encode(url.getUrlId())));

		url.setAccessCnt(
				urlOperationRepository.countByUrlIdAndOperation(url.getUrlId(), config.getUrlOperationAccessUrl()));

		url.setShortenCnt(
				urlOperationRepository.countByUrlIdAndOperation(url.getUrlId(), config.getUrlOperationShortenUrl()));

	}

	public void incrementShortenCnt(Url url, String userName) {
		urlOperationRepository
				.save(new UrlOperation(url.getUrlId(), userName, Instant.now(), config.getUrlOperationShortenUrl()));
	}

	public void incrementAccessCnt(Url url) {
		urlOperationRepository
				.save(new UrlOperation(url.getUrlId(), null, Instant.now(), config.getUrlOperationAccessUrl()));
	}

	public Url getUrl(long id) {
		Optional<Url> urlData = urlRepository.findById(id);
		if (urlData.isPresent()) {
			return urlData.get();
		} else {
			return null;
		}
	}

	public List<Url> getUserUrls(String userName) {
		return urlRepository.findByUserName(userName);
	}

	public void validateUrl(String url) {
		urlValidation.validateUrl(url);
	}

	public Optional<Url> findByLongUrl(String longUrl) {
		return urlRepository.findByLongUrl(longUrl);
	}

	public void insertNewUrl(Url url) {
		urlRepository.save(url);
	}

	public Url decodeAndGet(String encodedUrl) {
		return getUrl(urlEncoder.decode(encodedUrl));
	}
}
