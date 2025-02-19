package com.prography.tabletennis.domain.Initialization.controller.impl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.domain.Initialization.controller.InitializationController;
import com.prography.tabletennis.domain.Initialization.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.Initialization.service.InitializationService;
import com.prography.tabletennis.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("/init")
@RestController
@RequiredArgsConstructor
public class InitializationControllerImpl implements InitializationController {
  private final InitializationService initializationService;

  @PostMapping
  public ApiResponse<Void> initializeData(@RequestBody InitDataRequest initDataRequest) {
    initializationService.initializeDatabase(initDataRequest);
    return ApiResponse.success();
  }
}
