package com.queuesystem.exception;

import java.text.MessageFormat;

public class AppException extends Exception {

	private ErrorCode errorCode;
	private String[] params;

	public AppException(ErrorCode errorCode, String... params) {
		super(errorCode.toString());

		this.errorCode = errorCode;
		this.params = params;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getErrorText() {
		return MessageFormat.format(errorCode.getDescription(), params);
	}
}