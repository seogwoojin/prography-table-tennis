package com.prography.tabletennis.global.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.prography.tabletennis.global.response.ApiResponse;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<Void>> handleLbException(CustomException e) {
		log.error(e.getMessage());
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.fail(e.getReturnCode()));
	}

	@ExceptionHandler({
		HttpMessageNotReadableException.class,
		MissingServletRequestParameterException.class,
		MethodArgumentTypeMismatchException.class
	})
	public ResponseEntity<ApiResponse<Void>> handleRequestException(Exception e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.fail(ReturnCode.WRONG_PARAMETER));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodNotSupportedException() {
		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(ApiResponse.fail(ReturnCode.METHOD_NOT_ALLOWED));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<List<FieldError>>> badRequestExHandler(
		MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.fail(ReturnCode.WRONG_PARAMETER, bindingResult.getFieldErrors()));
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ApiResponse<Void>> handleServerException() {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.error(ReturnCode.INTERNAL_SERVER_ERROR));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<Void>> handleBusinessException() {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.error(ReturnCode.INTERNAL_SERVER_ERROR));
	}
}
