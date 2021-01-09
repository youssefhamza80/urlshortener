package com.youssef.testo.service;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlConfig;
import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.UrlOperation;
import com.youssef.testo.repository.UrlOperationRepository;
import com.youssef.testo.repository.UrlRepository;
import com.youssef.testo.util.Base62Encoder;

@Service
public class RedirectUrlService {

	private final Base62Encoder urlEncoder;

	private final UrlRepository urlDataRepository;

	private final UrlOperationRepository urlOperationRepository;

	private final ShortUrlConfig shortUrlConfig;

	public RedirectUrlService(UrlRepository urlDataRepository, UrlOperationRepository urlOperationRepository,
			Base62Encoder urlEncoder, ShortUrlConfig shortUrlConfig) {
		super();
		this.urlEncoder = urlEncoder;
		this.urlDataRepository = urlDataRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.shortUrlConfig = shortUrlConfig;
	}

	public ResponseEntity<String> redirect(String encodedUrl) {

		try {
			long id = urlEncoder.decode(encodedUrl);

			Url url = getUrl(id);

			if (url != null) {
				urlOperationRepository
						.save(new UrlOperation(id, null, Instant.now(), shortUrlConfig.getUrlOperationAccessUrl()));
			} else {
				throw new IllegalArgumentException("URL cannot be found");
			}
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getLongUrl())).build();
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	private Url getUrl(long id) {
		Optional<Url> urlData = urlDataRepository.findById(id);

		if (urlData.isPresent()) {
			return urlData.get();
		} else {
			return null;
		}
	}

}
