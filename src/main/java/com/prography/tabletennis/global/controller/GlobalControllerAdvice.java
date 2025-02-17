package com.prography.tabletennis.global.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prography.tabletennis.global.response.ApiResponse;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
  @ExceptionHandler(CustomException.class)
  public ApiResponse<Void> handleCustomException(CustomException e) {
    log.error(e.getMessage(), e);
    return ApiResponse.fail(e.getReturnCode());
  }

  @ExceptionHandler(RuntimeException.class)
  public ApiResponse<Void> handleBusinessException(RuntimeException e) {
    log.error(e.getMessage(), e);
    return ApiResponse.error(ReturnCode.SERVER_ERROR);
  }
}
