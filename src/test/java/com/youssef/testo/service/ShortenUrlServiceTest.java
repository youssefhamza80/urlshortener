package com.youssef.testo.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.youssef.testo.config.ShortUrlAppConfig;
import com.youssef.testo.entity.Url;
import com.youssef.testo.repository.UrlOperationRepository;
import com.youssef.testo.repository.UrlRepository;
import com.youssef.testo.util.Base62Encoder;
import com.youssef.testo.util.UrlValidation;

@SpringBootTest
@RunWith(SpringRunner.class)
class ShortenUrlServiceTest {

	private static final String baseRedirectUrl = "http://localhost:8080/r/%s";

	private static final String urlOperationAccessUrl = "A";
	private static final String urlOperationShortenUrl = "S";

	@Autowired
	ShortenUrlService shortenUrlService;

	@Autowired
	UrlValidation urlValidation;

	@Autowired
	Base62Encoder urlEncoder;

	@MockBean
	UrlRepository urlRepository;

	@MockBean
	UrlOperationRepository urlOperationRepository;

	@MockBean
	ShortUrlAppConfig config;

	@PostConstruct
	void setupConfig() {
		when(config.getBaseRedirectUrl()).thenReturn(baseRedirectUrl);
		when(config.getUrlOperationAccessUrl()).thenReturn(urlOperationAccessUrl);
		when(config.getUrlOperationShortenUrl()).thenReturn(urlOperationShortenUrl);
	}

	@Test
	void testShortenValidUrl_thenResultIsCreated() {
		String urlStr = "http://www.google.com";

		Url urlData = new Url(urlStr);

		urlData.setUrlId(1);
		urlData.setShortenCnt(1);
		urlData.setShortUrl(String.format(config.getBaseRedirectUrl(), urlEncoder.encode(urlData.getUrlId())));

		when(urlRepository.findByLongUrl(urlStr)).thenReturn(Optional.of(urlData));

		ResponseEntity<Object> expectedRespose = new ResponseEntity<>(urlData, HttpStatus.CREATED);

		ResponseEntity<Object> actualRespose = shortenUrlService.shortenUrl(urlStr, "user1");

		assertAll(() -> assertNotNull(actualRespose), () -> assertNotNull(actualRespose.getBody()),
				() -> assertEquals(expectedRespose.getBody(), actualRespose.getBody()));
	}
	
	@Test
	void testShortenValidUrlSecondTime_thenResultIsCreatedWithUpdatedShortenCnt() {
		String urlStr = "http://www.google.com";

		Url urlData = new Url(urlStr);

		urlData.setUrlId(1);
		urlData.setShortenCnt(2);
		urlData.setShortUrl(String.format(config.getBaseRedirectUrl(), urlEncoder.encode(urlData.getUrlId())));

		when(urlOperationRepository.countByUrlIdAndOperation(1, urlOperationShortenUrl)).thenReturn(2);

		when(urlRepository.findByLongUrl(urlStr)).thenReturn(Optional.of(urlData));
		
		ResponseEntity<Object> expectedRespose = new ResponseEntity<>(urlData, HttpStatus.CREATED);

		ResponseEntity<Object> actualRespose = shortenUrlService.shortenUrl(urlStr, "user1");

		assertAll(() -> assertNotNull(actualRespose), () -> assertNotNull(actualRespose.getBody()),
				() -> assertEquals(expectedRespose.getBody(), actualRespose.getBody()));
	}

	@Test
	void testShortenInvalidUrl_thenResultIsBadRequest() {
		String urlStr = "httpvv://www.google.com";

		when(urlRepository.findByLongUrl(urlStr)).thenReturn(Optional.empty());

		ResponseEntity<Object> expectedRespose = new ResponseEntity<>(UrlValidation.INVALID_URL,
				HttpStatus.BAD_REQUEST);

		ResponseEntity<Object> actualRespose = shortenUrlService.shortenUrl(urlStr, "user1");

		assertAll(() -> assertNotNull(actualRespose), () -> assertNotNull(actualRespose.getBody()),
				() -> assertEquals(expectedRespose.getBody(), actualRespose.getBody()));
	}
	
	@Test
	void testShortenEmptyUrl_thenResultIsBadRequest() {
		String urlStr = "";

		when(urlRepository.findByLongUrl(urlStr)).thenReturn(Optional.empty());

		ResponseEntity<Object> expectedRespose = new ResponseEntity<>(UrlValidation.EMPTY_URL,
				HttpStatus.BAD_REQUEST);

		ResponseEntity<Object> actualRespose = shortenUrlService.shortenUrl(urlStr, "user1");

		assertAll(() -> assertNotNull(actualRespose), () -> assertNotNull(actualRespose.getBody()),
				() -> assertEquals(expectedRespose.getBody(), actualRespose.getBody()));
	}
}
