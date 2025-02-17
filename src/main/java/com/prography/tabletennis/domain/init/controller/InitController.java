package com.prography.tabletennis.domain.init.controller;

import jakarta.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.domain.init.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.init.service.InitService;
import com.prography.tabletennis.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("/init")
@RestController
@RequiredArgsConstructor
public class InitController {
  private final InitService initService;

  @PostConstruct
  public void init() {
    initService.initializeDatabase(new InitDataRequest(3, 3));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> initializeData(
      @RequestBody InitDataRequest initDataRequest) {
    initService.initializeDatabase(initDataRequest);
    return ResponseEntity.ok(ApiResponse.success());
  }
}
