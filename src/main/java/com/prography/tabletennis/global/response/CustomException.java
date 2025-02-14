package com.prography.tabletennis.global.response;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ReturnCode returnCode;
	private final Integer code;
	private final String message;

	public CustomException(ReturnCode returnCode) {
		this.returnCode = returnCode;
		this.code = returnCode.getCode();
		this.message = returnCode.getMessage();
	}
}
