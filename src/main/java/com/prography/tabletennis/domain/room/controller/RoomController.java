package com.prography.tabletennis.domain.room.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
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
}
