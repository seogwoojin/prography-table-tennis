package com.prography.tabletennis.domain.team.controller.impl;

import org.springframework.web.bind.annotation.*;

import com.prography.tabletennis.domain.room.dto.request.UserInfoRequest;
import com.prography.tabletennis.domain.room.service.RoomService;
import com.prography.tabletennis.domain.team.controller.TeamController;
import com.prography.tabletennis.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamControllerImpl implements TeamController {
  private final RoomService roomService;

  @PutMapping("/{roomId}")
  public ApiResponse<Void> changeTeam(
      @PathVariable Integer roomId, @RequestBody UserInfoRequest userInfoRequest) {
    roomService.changeTeam(userInfoRequest.getUserId(), roomId);
    return ApiResponse.success();
  }
}
