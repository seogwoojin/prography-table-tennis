package com.prography.tabletennis.domain.team.controller;

import com.prography.tabletennis.domain.room.dto.request.UserInfoRequest;
import com.prography.tabletennis.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Team API", description = "팀 관련 API")
public interface TeamController {
  @Operation(
      summary = "팀 변경 API",
      description = "유저의 팀을 기존팀에서 반대팀으로 변경합니다.",
      responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = {
                      @ExampleObject(
                          name = "SUCCESS",
                          description = "팀 변경에 성공한 경우",
                          value = "{\n  \"code\": 200,\n  \"message\": \"API 요청이 성공했습니다.\"\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "팀을 변경할 수 없는 경우",
                          value = "{\n  \"code\": 201,\n  \"message\": \"불가능한 요청입니다.\"\n}"),
                    })),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "500",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = {
                      @ExampleObject(
                          name = "SERVER_ERROR",
                          description = "서버 에러가 발생한 경우",
                          value = "{\n  \"code\": 500,\n  \"message\": \"에러가 발생했습니다.\"\n}")
                    }))
      })
  ApiResponse<Void> changeTeam(Integer roomId, UserInfoRequest userInfoRequest);
}
