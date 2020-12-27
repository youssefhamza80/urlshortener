package com.youssef.testo.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonInclude(value = Include.NON_NULL)
public class ShortenUrlResult extends Result {
	private String url;
	private String shortUrl;

	private int urlShortenCnt;
	private int urlAccessCnt;

	private List<UserStatistics> userStatsList;

	public List<UserStatistics> getUserStatistics() {
		return userStatsList;
	}

	public void addUserStatistics(UserStatistics userStatistics) {
		if (userStatsList==null) {
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
		//this.userStatistics = userStatistics;
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
}
