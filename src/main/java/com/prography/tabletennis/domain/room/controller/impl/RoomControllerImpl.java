package com.prography.tabletennis.domain.room.controller.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import com.prography.tabletennis.domain.room.controller.RoomController;
import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.dto.request.UserInfoRequest;
import com.prography.tabletennis.domain.room.dto.response.RoomDetailInfoResponse;
import com.prography.tabletennis.domain.room.dto.response.RoomPageResponse;
import com.prography.tabletennis.domain.room.service.RoomService;
import com.prography.tabletennis.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomControllerImpl implements RoomController {
  private final RoomService roomService;

  @PostMapping
  public ApiResponse<Void> createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
    roomService.createNewRoom(createRoomRequest);
    return ApiResponse.success();
  }

  @GetMapping
  public ApiResponse<RoomPageResponse> getRoomInfos(
      @RequestParam int size, @RequestParam int page) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
    RoomPageResponse response = roomService.getRoomInfos(pageRequest);
    return ApiResponse.success(response);
  }

  @GetMapping("/{roomId}")
  public ApiResponse<RoomDetailInfoResponse> getRoomDetail(@PathVariable Integer roomId) {
    RoomDetailInfoResponse response = roomService.getRoomDetailInfo(roomId);
    return ApiResponse.success(response);
  }

  @PostMapping("/attention/{roomId}")
  public ApiResponse<Void> joinRoom(
      @PathVariable Integer roomId, @RequestBody UserInfoRequest userInfoRequest) {
    roomService.joinRoom(userInfoRequest.getUserId(), roomId);
    return ApiResponse.success();
  }

  @PostMapping("/out/{roomId}")
  public ApiResponse<Void> exitRoom(
      @PathVariable Integer roomId, @RequestBody UserInfoRequest userInfoRequest) {
    roomService.exitRoom(userInfoRequest.getUserId(), roomId);
    return ApiResponse.success();
  }

  @PutMapping("/start/{roomId}")
  public ApiResponse<Void> startGame(
      @PathVariable Integer roomId, @RequestBody UserInfoRequest userInfoRequest) {
    roomService.startGame(userInfoRequest.getUserId(), roomId);
    return ApiResponse.success();
  }

  @PutMapping("/{roomId}")
  public ApiResponse<Void> changeTeam(
      @PathVariable Integer roomId, @RequestBody UserInfoRequest userInfoRequest) {
    roomService.changeTeam(userInfoRequest.getUserId(), roomId);
    return ApiResponse.success();
  }
}
