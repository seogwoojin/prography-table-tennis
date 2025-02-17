package com.prography.tabletennis.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/health")
@Tag(name = "Health")
public class HealthCheckController {
  @GetMapping
  public ResponseEntity<ApiResponse<Void>> checkServerHealth() {
    return ResponseEntity.ok(ApiResponse.success());
  }
}
