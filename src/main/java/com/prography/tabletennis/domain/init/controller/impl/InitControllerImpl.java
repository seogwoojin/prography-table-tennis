package com.prography.tabletennis.domain.init.controller.impl;

import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.domain.init.controller.InitController;
import com.prography.tabletennis.domain.init.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.init.service.InitService;
import com.prography.tabletennis.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("/init")
@RestController
@RequiredArgsConstructor
public class InitControllerImpl implements InitController {
  private final InitService initService;

  @PostConstruct
  public void init() {
    initService.initializeDatabase(new InitDataRequest(3, 3));
  }

  @PostMapping
  public ApiResponse<Void> initializeData(@RequestBody InitDataRequest initDataRequest) {
    initService.initializeDatabase(initDataRequest);
    return ApiResponse.success();
  }
}
