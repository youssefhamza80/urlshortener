package com.youssef.testo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.youssef.testo.config.ShortUrlConfig;
import com.youssef.testo.config.UrlOperationConfigProperties;
import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.UrlOperation;
import com.youssef.testo.entity.User;
import com.youssef.testo.repository.UrlDataRepository;
import com.youssef.testo.repository.UrlOperationRepository;
import com.youssef.testo.service.dto.ShortenUrlResult;
import com.youssef.testo.service.dto.UserStatistics;
import com.youssef.testo.util.UrlValidator;

@Service
public class ShortenUrlService {

	private final UserService userService;

	private final UrlValidator urlValidator;

	private final UrlDataRepository urlDataRepository;

	private final UrlOperationRepository urlOperationRepository;

	private final ShortUrlConfig configProperties;

	private final UrlOperationConfigProperties urlOperationConfigProperties;

	public ShortenUrlService(UrlDataRepository urlDataRepository, UrlOperationRepository urlOperationRepository,
			ShortUrlConfig configProperties, UrlOperationConfigProperties urlOperationConfigProperties,
			UrlValidator urlValidator, UserService userService) {
		super();
		this.userService = userService;
		this.urlValidator = urlValidator;
		this.urlDataRepository = urlDataRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.configProperties = configProperties;
		this.urlOperationConfigProperties = urlOperationConfigProperties;
	}

	public ShortenUrlResult shortenUrl(String url, String userName) {
		try {
			ShortenUrlResult result = new ShortenUrlResult();
			List<Url> shortendUrlList = urlDataRepository.findByLongUrl(url);
			Url urlData = null;
			long userId = -1;
			if (shortendUrlList.size() > 0) {
				urlData = shortendUrlList.get(0);
			} else {
				urlValidator.validateUrl(url);
				urlData = new Url(url);
				urlDataRepository.save(urlData);
			}

			UserStatistics userStatistics = null;
			if (userName != null) {

				User user = userService.getUserByName(userName);

				if (user == null) {
					throw new Exception("Invalid user");
				}
				userId = user.getId();
				urlOperationRepository.save(new UrlOperation(urlData.getId(), userId, LocalDateTime.now(),
						urlOperationConfigProperties.getShortenUrl()));

				int userShortenedCnt = urlOperationRepository.countByUrlIdAndOperationAndUserId(urlData.getId(),
						urlOperationConfigProperties.getShortenUrl(), userId);

				int userAccessedCnt = urlOperationRepository.countByUrlIdAndOperationAndUserId(urlData.getId(),
						urlOperationConfigProperties.getAccessUrl(), userId);

				userStatistics = new UserStatistics(userId, userShortenedCnt, userAccessedCnt);
			} else {
				urlOperationRepository.save(new UrlOperation(urlData.getId(), userId, LocalDateTime.now(),
						urlOperationConfigProperties.getShortenUrl()));
			}

			result.setResult(configProperties.getSuccessResult());
			result.setUrl(url);
			result.setShortUrl(configProperties.getBaseUrl() + urlData.getId());
			result.setUrlShortenCnt(urlOperationRepository.countByUrlIdAndOperation(urlData.getId(),
					urlOperationConfigProperties.getShortenUrl()));
			result.setUrlAccessCnt(urlOperationRepository.countByUrlIdAndOperation(urlData.getId(),
					urlOperationConfigProperties.getAccessUrl()));
			result.addUserStatistics(userStatistics);
			return result;
		}

		catch (Exception ex) {
			ShortenUrlResult result = new ShortenUrlResult();
			result.setResult(configProperties.getErrorResult());
			result.setUrl(url);
			result.setErrorDetails(ex.getMessage());
			return result;
		}
	}

}
