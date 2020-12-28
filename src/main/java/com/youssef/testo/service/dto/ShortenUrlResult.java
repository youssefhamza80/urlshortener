package com.youssef.testo.service.dto;

import java.util.ArrayList;
import java.util.List;

//@JsonInclude(value = Include.NON_NULL)
@SuppressWarnings("unused")
public class ShortenUrlResult extends Result {
	private String url;
	private String shortUrl;

	private String userSpecificShortUrl;

	private int urlShortenCnt;
	private int urlAccessCnt;

	private List<UserStatistics> userStatsList;

	public List<UserStatistics> getUserStatistics() {
		return userStatsList;
	}

	public void addUserStatistics(UserStatistics userStatistics) {
		if (userStatsList == null) {
			userStatsList = new ArrayList<>();
		}
		userStatsList.add(userStatistics);
	}

	public ShortenUrlResult() {
		super();
	}

	public ShortenUrlResult(String url, String shortUrl, int urlShortenCnt, int urlAccessCnt) {
		super();
		this.url = url;
		this.shortUrl = shortUrl;
		this.urlShortenCnt = urlShortenCnt;
		this.urlAccessCnt = urlAccessCnt;
	}

	public int getUrlShortenCnt() {
		return urlShortenCnt;
	}

	public void setUrlShortenCnt(int urlShortenCnt) {
		this.urlShortenCnt = urlShortenCnt;
	}

	public int getUrlAccessCnt() {
		return urlAccessCnt;
	}

	public void setUrlAccessCnt(int urlAccessCnt) {
		this.urlAccessCnt = urlAccessCnt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getUserSpecificShortUrl() {
		return userSpecificShortUrl;
	}

	public void setUserSpecificShortUrl(String userSpecificShortUrl) {
		this.userSpecificShortUrl = userSpecificShortUrl;
	}
}
