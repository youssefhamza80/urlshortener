package com.youssef.testo.service.dto;

public class Result {
	String result;
	String errorDetails;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	
	
	
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Result(String result, String errorDetails) {
		super();
		this.result = result;
		this.errorDetails = errorDetails;
	}
	
}
