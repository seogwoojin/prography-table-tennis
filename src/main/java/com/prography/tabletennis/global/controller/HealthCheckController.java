package com.prography.tabletennis.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.global.response.ApiResponse;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/health")
@Tag(name = "Health")
public class HealthCheckController {
	@GetMapping
	public ResponseEntity<ApiResponse<Void>> checkServerHealth() {
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@GetMapping("/test")
	public String testException() {
		// GlobalAdvice에서 처리할 CustomException 발생
		throw new CustomException(ReturnCode.EXTERNAL_SERVER_ERROR);
	}
}
