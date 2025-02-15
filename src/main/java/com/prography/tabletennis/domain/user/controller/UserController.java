package com.prography.tabletennis.domain.user.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.domain.user.dto.response.UserResultResponse;
import com.prography.tabletennis.domain.user.service.UserService;
import com.prography.tabletennis.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<ApiResponse<UserResultResponse>> getUserInfos(
		@RequestParam int size,
		@RequestParam int page
	) {
		PageRequest pageRequest = PageRequest.of(size, page, Sort.by("id"));
		UserResultResponse response = userService.getUserInfos(pageRequest);
		return ResponseEntity.ok().body(ApiResponse.success(response));
	}
}
