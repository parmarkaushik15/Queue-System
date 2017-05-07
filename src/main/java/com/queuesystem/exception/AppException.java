package com.queuesystem.exception;

public class AppException extends Exception {

	private ErrorCode errorCode;

	public AppException(ErrorCode errorCode) {
		super(errorCode.toString());

		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}