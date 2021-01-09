package com.youssef.testo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlConfig;
import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.UrlOperation;
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

	private final ShortUrlConfig shortUrlConfig;

	private final Base62Encoder base62Encoder;

	public StatisticsService(UrlRepository urlRepository, ShortUrlConfig shortUrlConfig,
			UrlOperationRepository urlOperationRepository, UserRepository userRepository, Base62Encoder base62Encoder) {
		this.urlRepository = urlRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.shortUrlConfig = shortUrlConfig;
		this.base62Encoder = base62Encoder;
		this.userRepository = userRepository;
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

	public ResponseEntity<Object> getAllStatistics() {

		try {
			Iterable<User> users = userRepository.findAll();
			users.forEach(this::addUserUrls);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void addUserUrls(User user) {

		List<Url> userUrls = urlRepository.findByUserName(user.getUserName());

		userUrls.forEach(url -> addUrlToUser(user, url));

	}

	private void addUrlToUser(User user, Url url) {
		int accessCnt = urlOperationRepository.countByUrlIdAndOperation(url.getUrlId(),
				shortUrlConfig.getUrlOperationAccessUrl());
		int shortenCnt = urlOperationRepository.countByUrlIdAndOperation(url.getUrlId(),
				shortUrlConfig.getUrlOperationShortenUrl());
		url.setShortenCnt(shortenCnt);
		url.setAccessCnt(accessCnt);
		url.setShortUrl(String.format(shortUrlConfig.getBaseRedirectUrl(), base62Encoder.encode(url.getUrlId())));
		user.addUrl(url);
	}

}
