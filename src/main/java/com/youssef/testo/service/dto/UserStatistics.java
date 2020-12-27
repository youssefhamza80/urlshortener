package com.youssef.testo.service.dto;

public class UserStatistics {

	private long userId;
	
	private int urlShortenCnt;
	
	private int urlAccessCnt;

	public UserStatistics(long userId, int urlShortenCnt, int urlAccessCnt) {
		super();
		this.userId = userId;
		this.urlShortenCnt = urlShortenCnt;
		this.urlAccessCnt = urlAccessCnt;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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
}
