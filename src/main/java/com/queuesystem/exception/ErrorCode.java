package com.queuesystem.exception;

public enum ErrorCode {

	QUEUE_IS_NOT_ACTIVE("Queue: {0} Is Not in Active Status"),

	QUEUE_DOES_NOT_EXISTS("Queue: {0} does not exists with that name"),

	QUEUE_IS_ALREADY_IN_STATUS("Queue: {0} is Already in {1} status"),

	QUEUE_NAME_IS_USED("Queue with name:{0} already exists");

	private String description;

	ErrorCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}