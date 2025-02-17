package com.prography.tabletennis.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private final Integer code;
  private final String message;
  private final T result;

  private ApiResponse(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.result = data;
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMessage(), data);
  }

  public static <T> ApiResponse<T> success() {
    return success(null);
  }

  public static <T> ApiResponse<T> fail(ReturnCode returnCode) {
    return fail(returnCode, null);
  }

  public static <T> ApiResponse<T> fail(ReturnCode returnCode, T data) {
    return new ApiResponse<>(returnCode.getCode(), returnCode.getMessage(), data);
  }

  public static <T> ApiResponse<T> error(ReturnCode returnCode) {
    return new ApiResponse<>(returnCode.getCode(), returnCode.getMessage(), null);
  }
}
