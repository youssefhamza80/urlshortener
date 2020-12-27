package com.youssef.testo.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	long id;

	public long getId() {
		return id;
	}

	@Column(name = "LONG_URL")
	String longUrl;
	
	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
}
