package com.youssef.testo.service;

import java.time.LocalDateTime;
import java.util.List;
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
import com.youssef.testo.service.dto.ShortenUrlResult;
import com.youssef.testo.service.dto.UserStatistics;
import com.youssef.testo.util.Base62Encoder;
import com.youssef.testo.util.UrlValidation;

@Service
public class ShortenUrlService {

	private final Base62Encoder urlEncoder;

	private final UserService userService;

	private final UrlValidation urlValidator;

	private final UrlDataRepository urlDataRepository;

	private final UrlOperationRepository urlOperationRepository;

	private final ShortUrlConfig shortUrlConfig;

	public ShortenUrlService(UrlDataRepository urlDataRepository, UrlOperationRepository urlOperationRepository,
			ShortUrlConfig shortUrlConfig, UrlValidation urlValidator, UserService userService,
			Base62Encoder urlEncoder) {
		super();
		this.urlEncoder = urlEncoder;
		this.userService = userService;
		this.urlValidator = urlValidator;
		this.urlDataRepository = urlDataRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.shortUrlConfig = shortUrlConfig;
	}

	public ResponseEntity<ShortenUrlResult> shortenUrl(String longUrl, String userName) {
		try {
			ShortenUrlResult result = new ShortenUrlResult();
			Optional<Url> shortendUrl = urlDataRepository.findByLongUrl(longUrl);
			Url url = null;
			long userId = -1;
			if (shortendUrl.isPresent()) {
				url = shortendUrl.get();
			} else {
				urlValidator.validateUrl(longUrl);
				url = new Url(longUrl);
				urlDataRepository.save(url);
			}

			UserStatistics userStatistics = null;
			if (userName != null) {
				User user = userService.getUserByName(userName);

				if (user == null) {
					throw new IllegalArgumentException("Invalid user: " + userName);
				}
				userId = user.getId();

				urlOperationRepository.save(new UrlOperation(url.getId(), userId, LocalDateTime.now(),
						shortUrlConfig.getUrlOperationShortenUrl()));

				int userShortenedCnt = urlOperationRepository.countByUrlIdAndOperationAndUserId(url.getId(),
						shortUrlConfig.getUrlOperationShortenUrl(), userId);

				int userAccessedCnt = urlOperationRepository.countByUrlIdAndOperationAndUserId(url.getId(),
						shortUrlConfig.getUrlOperationAccessUrl(), userId);

				userStatistics = new UserStatistics(userId, userShortenedCnt, userAccessedCnt);
			} else {
				urlOperationRepository.save(new UrlOperation(url.getId(), userId, LocalDateTime.now(),
						shortUrlConfig.getUrlOperationShortenUrl()));
			}

			result.setStatus(shortUrlConfig.getSuccessResult());
			result.setUrl(longUrl);

			if (userName != null) {
				result.setUserSpecificShortUrl(String.format(shortUrlConfig.getUserSpecificRedirectUrl(), userName,
						urlEncoder.encode(url.getId())));
			}
			result.setShortUrl(String.format(shortUrlConfig.getRedirectUrl(), urlEncoder.encode(url.getId())));
			result.setUrlShortenCnt(urlOperationRepository.countByUrlIdAndOperation(url.getId(),
					shortUrlConfig.getUrlOperationShortenUrl()));
			result.setUrlAccessCnt(urlOperationRepository.countByUrlIdAndOperation(url.getId(),
					shortUrlConfig.getUrlOperationAccessUrl()));
			if (userStatistics != null)
				result.addUserStatistics(userStatistics);

			return new ResponseEntity<>(result, HttpStatus.CREATED);
		}

		catch (Exception ex) {
			ShortenUrlResult result = new ShortenUrlResult();
			result.setStatus(shortUrlConfig.getErrorResult());
			result.setUrl(longUrl);
			result.setErrorDetails(ex.getMessage());
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}
}
