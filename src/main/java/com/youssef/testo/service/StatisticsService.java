package com.youssef.testo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.User;
import com.youssef.testo.repository.UserRepository;

@Service
public class StatisticsService {
	
	private final UrlService urlService;

	private final UserRepository userRepository;

	public StatisticsService(
			UserRepository userRepository, UrlService urlService) {
		this.urlService = urlService;
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
		List<Url> userUrls = urlService.getUserUrls(user.getUserName());
		
		userUrls.forEach(urlService::populateUrlAttributes);

		user.setUrls(userUrls);
	}
}
