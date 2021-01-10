package com.youssef.testo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlAppConfig;
import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.User;
import com.youssef.testo.repository.UrlOperationRepository;
import com.youssef.testo.repository.UrlRepository;
import com.youssef.testo.repository.UserRepository;
import com.youssef.testo.util.Base62Encoder;

@Service
public class StatisticsService {

	private final UserRepository userRepository;

	private final UrlRepository urlRepository;

	private final UrlOperationRepository urlOperationRepository;

	private final ShortUrlAppConfig appConfig;

	private final Base62Encoder urlEncoder;

	public StatisticsService(UrlRepository urlRepository, ShortUrlAppConfig appConfig,
			UrlOperationRepository urlOperationRepository, UserRepository userRepository, Base62Encoder base62Encoder) {
		this.urlRepository = urlRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.appConfig = appConfig;
		this.urlEncoder = base62Encoder;
		this.userRepository = userRepository;
	}

	public ResponseEntity<Object> getAllStatistics() {

		try {
			Iterable<User> users = userRepository.findAll();
			users.forEach(this::addUserUrls);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> getUserStatistics(String userName) {
		try {
			Optional<User> user = userRepository.findById(userName);
			if (user.isPresent()) {
				addUserUrls(user.get());
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User " + userName + " does not exist", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void addUserUrls(User user) {
		List<Url> userUrls = urlRepository.findByUserName(user.getUserName());
		
		userUrls.forEach(this::populateUrlProperties);

		user.setUrls(userUrls);
	}

	private void populateUrlProperties(Url url) {
		int accessCnt = urlOperationRepository.countByUrlIdAndOperation(url.getUrlId(),
				appConfig.getUrlOperationAccessUrl());
		int shortenCnt = urlOperationRepository.countByUrlIdAndOperation(url.getUrlId(),
				appConfig.getUrlOperationShortenUrl());
		url.setShortenCnt(shortenCnt);
		url.setAccessCnt(accessCnt);
		url.setShortUrl(String.format(appConfig.getBaseRedirectUrl(), urlEncoder.encode(url.getUrlId())));
	}
}
