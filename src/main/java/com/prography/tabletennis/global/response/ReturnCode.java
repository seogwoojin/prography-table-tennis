package com.prography.tabletennis.global.response;

import lombok.Getter;

@Getter
public enum ReturnCode {
  // 성공 요청
  SUCCESS(200, "API 요청이 성공했습니다."),
  // 잘못된 요청
  WRONG_REQUEST(201, "불가능한 요청입니다."),
  // 서버 예외
  SERVER_ERROR(500, "에러가 발생했습니다.");

  ReturnCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  private final Integer code;
  private final String message;
}
