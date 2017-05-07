package com.queuesystem.exception;

public enum ErrorCode {

	QUEUE_IS_NOT_ACTIVE("Queue Is Not in Active Status"),

	QUEUE_DOES_NOT_EXISTS("Queue does not exsists with that name");

	private String description;

	ErrorCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}