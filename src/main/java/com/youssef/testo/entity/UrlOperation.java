package com.youssef.testo.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "URL_OPERATION")
public class UrlOperation {

	@Id
	@Column(name = "URL_OPERATION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column(name = "URL_ID")
	long urlId;

	@Column(name = "USER_NAME")
	String userName;

	@Column(name = "OPERATION_DATE")
	Instant operationDate;

	public UrlOperation() {
		super();
	}

	@Column(name = "OPERATION")
	String operation;

	public UrlOperation(long urlId, String userName, Instant operationDate, String operation) {
		super();
		this.urlId = urlId;
		this.userName = userName;
		this.operationDate = operationDate;
		this.operation = operation;
	}

	public long getUrlId() {
		return urlId;
	}

	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Instant getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Instant operationDate) {
		this.operationDate = operationDate;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperationId(String operation) {
		this.operation = operation;
	}

	public long getId() {
		return id;
	}
}
