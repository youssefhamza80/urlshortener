package com.youssef.testo.service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlConfig;
import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.UrlOperation;
import com.youssef.testo.entity.User;
import com.youssef.testo.repository.UrlDataRepository;
import com.youssef.testo.repository.UrlOperationRepository;
import com.youssef.testo.util.Base62Encoder;

@Service
public class RedirectUrlService {

	private final Base62Encoder urlEncoder;

	private final UserService userService;

	private final UrlDataRepository urlDataRepository;

	private final UrlOperationRepository urlOperationRepository;
	
	private final ShortUrlConfig shortUrlConfig;

	public RedirectUrlService(UrlDataRepository urlDataRepository, UrlOperationRepository urlOperationRepository,
			UserService userService,
			Base62Encoder urlEncoder, ShortUrlConfig shortUrlConfig) {
		super();
		this.urlEncoder = urlEncoder;
		this.userService = userService;
		this.urlDataRepository = urlDataRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.shortUrlConfig = shortUrlConfig;
	}

	public ResponseEntity<String> redirect(String encodedUrl, String userName) {

		try {
			long userId = -1;
			if (userName != null) {
				User user = userService.getUserByName(userName);
				if (user != null) {
					userId = user.getId();
				} else {
					throw new IllegalArgumentException("Cannot find this user: " + userName);
				}
			}

			long id = urlEncoder.decode(encodedUrl);

			Url url = getUrl(id);

			if (url != null) {
				urlOperationRepository.save(
						new UrlOperation(id, userId, LocalDateTime.now(), shortUrlConfig.getUrlOperationAccessUrl()));
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
