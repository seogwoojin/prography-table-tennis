package com.prography.tabletennis.domain.room.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.dto.response.RoomPageResponse;
import com.prography.tabletennis.domain.room.service.RoomService;
import com.prography.tabletennis.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
	private final RoomService roomService;

	@PostMapping
	public ApiResponse<Void> createRoom(
		@RequestBody CreateRoomRequest createRoomRequest
	) {
		roomService.createNewRoom(createRoomRequest);
		return ApiResponse.success();
	}

	@GetMapping
	public ApiResponse<RoomPageResponse> getRoomInfos(
		@RequestParam int size,
		@RequestParam int page
	) {
		PageRequest pageRequest = PageRequest.of(size, page, Sort.by("id"));
		RoomPageResponse response = roomService.getRoomInfos(pageRequest);
		return ApiResponse.success(response);
	}
}
