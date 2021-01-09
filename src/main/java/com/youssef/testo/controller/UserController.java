package com.youssef.testo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.testo.config.AuthenticationFacade;
import com.youssef.testo.service.ShortenUrlService;
import com.youssef.testo.service.StatisticsService;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')||hasRole('USER')")
public class UserController {

	private final AuthenticationFacade authenticationFacade;

	private final ShortenUrlService shortenUrlService;

	private final StatisticsService statisticsService;

	public UserController(ShortenUrlService shortenUrlService, StatisticsService statisticsService,
			AuthenticationFacade authenticationFacade) {
		super();
		this.authenticationFacade = authenticationFacade;
		this.shortenUrlService = shortenUrlService;
		this.statisticsService = statisticsService;
	}

	private boolean isCurrentUserMatchesUrl(String userName) {
		Authentication authentication = authenticationFacade.getAuthentication();

		if (userName != null && authentication != null) {
			return userName.equals(authentication.getName());
		}

		return false;
	}

	@PostMapping("/{userName}/shortenurl")
	@PreAuthorize("hasRole('USER')||hasRole('ADMIN')")
	public ResponseEntity<Object> getShortendUrl(@PathVariable String userName, @RequestBody String url) {
		boolean userAuthorized = isCurrentUserMatchesUrl(userName);
		if (userAuthorized)
			return shortenUrlService.shortenUrl(url, userName);
		else {
			return new ResponseEntity<>("You are not authorized to view this page", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/{userName}/statistics")
	@PreAuthorize("hasRole('USER')||hasRole('ADMIN')")
	public ResponseEntity<Object> getStatistics(@PathVariable String userName) {
		boolean userAuthorized = isCurrentUserMatchesUrl(userName);
		if (userAuthorized) {
			return statisticsService.getUserStatistics(userName);
		} else {
			return new ResponseEntity<>("You are not authorized to view this page", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("{userName}/allstatistics")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getAllStatistics(@PathVariable String userName) {
		boolean userAuthorized = isCurrentUserMatchesUrl(userName);
		if (userAuthorized) {
			return statisticsService.getAllStatistics();
		} else {
			return new ResponseEntity<>("You are not authorized to view this page", HttpStatus.UNAUTHORIZED);
		}
	}
}
