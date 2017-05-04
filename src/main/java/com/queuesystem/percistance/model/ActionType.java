package com.queuesystem.percistance.model;

public enum ActionType {

	QUEUE_ADD("Queue Addition"),

	QUEUE_DELETE("Queue Deletion"),

	QUEUE_UPDATE("Updating Queue"),

	QUEUE_UP("Queue Up"),

	QUEUE_LEAVING("Leaving Queue");

	private String action;

	ActionType(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}