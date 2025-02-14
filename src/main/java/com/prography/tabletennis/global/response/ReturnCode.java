package com.prography.tabletennis.global.response;

import lombok.Getter;

@Getter
public enum ReturnCode {
	SUCCESS(200, "API 요청이 성공했습니다."),
	// 서버 에러
	WRONG_PARAMETER(8000, "잘못된 파라미터 입니다."),
	METHOD_NOT_ALLOWED(8001, "허용되지 않은 메소드 입니다."),
	INTERNAL_SERVER_ERROR(9998, "내부 서버 에러 입니다."),
	EXTERNAL_SERVER_ERROR(9999, "외부 서버 에러 입니다.");

	ReturnCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	private final Integer code;
	private final String message;

}
