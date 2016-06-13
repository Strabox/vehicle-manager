package com.pt.pires.controllers;

public class ResponseError {

	private int code;
	
	private String description;
	
	public ResponseError(int code,String description){
		setCode(code);
		setDescription(description);
	}
	
	public ResponseError() { }	//Needed for JPA/JSON
	
	/* === Getters and Setters === */
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
