package com.increff.employee.model;

import java.time.LocalDateTime;

public class InfoData {

	private String message;

	public InfoData() {
		message = "Activity time: " + LocalDateTime.now().toString();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
