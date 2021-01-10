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

	@Column(name = "LONG_URL")
	String longUrl;

	@Id
	@Column(name = "URL_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long urlId;

	@Transient
	private String shortUrl;

	@Transient
	private long shortenCnt;

	@Transient
	private long accessCnt;

	public long getUrlId() {
		return urlId;
	}

	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Url)) {
			return false;
		}

		Url urlObj = (Url) obj;

		return urlObj.urlId == this.urlId
				&& (urlObj.longUrl != null && this.longUrl != null || (urlObj.longUrl == null && this.longUrl == null))
				&& urlObj.longUrl == this.longUrl && urlObj.shortenCnt == this.shortenCnt
				&& urlObj.accessCnt == this.accessCnt;
	}
}
