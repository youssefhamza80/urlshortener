package com.youssef.testo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.youssef.testo.config.UrlOperationConfigProperties;
import com.youssef.testo.entity.Url;
import com.youssef.testo.entity.UrlOperation;
import com.youssef.testo.entity.User;
import com.youssef.testo.repository.UrlDataRepository;
import com.youssef.testo.repository.UrlOperationRepository;

@Service
public class RedirectUrlService {

	private final UserService userService;

	private final UrlDataRepository urlDataRepository;

	private final UrlOperationRepository urlOperationRepository;

	private final UrlOperationConfigProperties urlOperationConfigProperties;

	public RedirectUrlService(UrlDataRepository urlDataRepository, UrlOperationRepository urlOperationRepository,
			UrlOperationConfigProperties urlOperationConfigProperties, UserService userService) {
		super();
		this.userService = userService;
		this.urlDataRepository = urlDataRepository;
		this.urlOperationRepository = urlOperationRepository;
		this.urlOperationConfigProperties = urlOperationConfigProperties;
	}

	private Url getUrl(long id) {
		Optional<Url> urlData = urlDataRepository.findById(id);

		if (urlData.isPresent()) {
			return urlData.get();
		} else {
			return null;
		}
	}

	public Url getUrlAndIncrementAccessCnt(long id, String userName) throws Exception {

		long userId = -1;
		if (userName != null) {
			User user = userService.getUserByName(userName);
			if (user != null) {
				userId = user.getId();
			} else {
				throw new Exception("Cannot find this user: " + userName);
			}
		}

		Url url = getUrl(id);

		if (url != null) {
			urlOperationRepository.save(
					new UrlOperation(id, userId, LocalDateTime.now(), urlOperationConfigProperties.getAccessUrl()));
		} else {
			throw new Exception("URL cannot be found");
		}
		return url;
	}
}
