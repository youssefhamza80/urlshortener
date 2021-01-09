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

import com.youssef.testo.security.AuthenticationFacade;
import com.youssef.testo.service.ShortenUrlService;
import com.youssef.testo.service.StatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Controller APIs", tags = { "User Controller" })
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

	@ApiOperation(value = "Shorten a URL by a specific user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping("/{userName}/shortenurl")
	@PreAuthorize("hasRole('USER')||hasRole('ADMIN')")
	public ResponseEntity<Object> shortenUrl(@PathVariable String userName, @RequestBody String url) {
		boolean userAuthorized = isCurrentUserMatchesUrl(userName);
		if (userAuthorized)
			return shortenUrlService.shortenUrl(url, userName);
		else {
			return new ResponseEntity<>("You are not authorized to view this page", HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "Get URL statistics for a specific user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
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

	@ApiOperation(value = "Get all URL statistics for users with ADMIN role only")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
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
