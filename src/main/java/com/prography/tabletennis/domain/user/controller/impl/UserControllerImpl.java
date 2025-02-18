package com.prography.tabletennis.domain.user.controller.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.domain.user.controller.UserController;
import com.prography.tabletennis.domain.user.dto.response.UserPageResponse;
import com.prography.tabletennis.domain.user.service.UserService;
import com.prography.tabletennis.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
  private final UserService userService;

  @GetMapping
  public ApiResponse<UserPageResponse> getUserInfos(
      @RequestParam int size, @RequestParam int page) {
    PageRequest pageRequest = PageRequest.of(size, page, Sort.by("id"));
    UserPageResponse response = userService.getUserInfos(pageRequest);
    return ApiResponse.success(response);
  }
}
