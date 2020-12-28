package com.youssef.testo.service.dto;

public class Result {
	String status;
	String errorDetails;
	public String getStatus() {
		return status;
	}
	public void setStatus(String result) {
		this.status = result;
	}
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	
	
	
	public Result() {
		super();
	}
	public Result(String result, String errorDetails) {
		super();
		this.status = result;
		this.errorDetails = errorDetails;
	}
}
