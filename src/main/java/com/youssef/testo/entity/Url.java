package com.youssef.testo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "URL")
public class Url {
	
	public Url() {
		super();
	}
	
	public Url(String longUrl) {
		super();
		this.longUrl = longUrl;
	}
	
	@Id
	@Column(name = "URL_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long urlId;

	public long getUrlId() {
		return urlId;
	}
	
	@Column(name = "LONG_URL")
	String longUrl;
	
	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	
	@Transient
	private String shortUrl;
	
	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	@Transient
	private long shortenCnt;
	
	public long getShortenCnt() {
		return shortenCnt;
	}

	public void setShortenCnt(long shortenCnt) {
		this.shortenCnt = shortenCnt;
	}

	public long getAccessCnt() {
		return accessCnt;
	}

	public void setAccessCnt(long accessCnt) {
		this.accessCnt = accessCnt;
	}

	@Transient
	private long accessCnt;
}
