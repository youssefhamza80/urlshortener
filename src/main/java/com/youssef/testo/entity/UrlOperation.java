package com.youssef.testo.entity;

import java.time.LocalDateTime;

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
	
	@Column(name = "USER_ID")
	long userId;
	
	@Column(name = "OPERATION_DATE")
	LocalDateTime operationDate;
	
	public UrlOperation() {
		super();
	}

	@Column(name = "OPERATION")
	String operation;

	public UrlOperation(long urlId, long userId, LocalDateTime operationDate, String operation) {
		super();		
		this.urlId = urlId;
		this.userId = userId;
		this.operationDate = operationDate;
		this.operation = operation;
	}

	public long getUrlId() {
		return urlId;
	}

	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public LocalDateTime getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(LocalDateTime operationDate) {
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
