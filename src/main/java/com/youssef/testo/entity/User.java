package com.youssef.testo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "USER")
@JsonInclude(Include.NON_NULL)
public class User {

	public User() {
		urls = new ArrayList<>();
	}

	@Id
	@Column(name = "USER_NAME")
	String userName;

	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column(name = "FULL_NAME")
	String fullName;

	@Column(name = "CREATION_DATE")
	LocalDateTime creationDate;

	@Column(name = "EMAIL")
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Transient
	List<Url> urls;

	public void addUrl(Url url) {
		urls.add(url);
	}

	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
}
