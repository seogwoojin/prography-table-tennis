package com.prography.tabletennis.domain.user.controller;

import com.prography.tabletennis.domain.user.dto.response.UserPageResponse;
import com.prography.tabletennis.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User API", description = "유저 관련 API")
public interface UserController {
  @Operation(
      summary = "유저 전체 조회",
      description = "페이징 처리된 유저 정보를 조회합니다.",
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
                          description = "유저 정보가 조회된 경우",
                          value =
                              "{\n  \"code\": 200,\n  \"message\": \"API 요청이 성공했습니다.\",\n  \"result\": {\n    \"totalElements\": 12,\n    \"totalPages\": 2,\n    \"userList\": [\n      {\n        \"id\": 11,\n        \"fakerId\": 11,\n        \"name\": \"jung.jongju\",\n        \"email\": \"ghong@heo.com\",\n        \"status\": \"ACTIVE\",\n        \"createdAt\": \"2025-02-21 13:30:44\",\n        \"updatedAt\": \"2025-02-21 13:30:44\"\n      },\n      {\n        \"id\": 12,\n        \"fakerId\": 12,\n        \"name\": \"suyoun34\",\n        \"email\": \"yoon.hyungcheol@jung.net\",\n        \"status\": \"ACTIVE\",\n        \"createdAt\": \"2025-02-21 13:30:44\",\n        \"updatedAt\": \"2025-02-21 13:30:44\"\n      }\n    ]\n  }\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "잘못된 API 호출인 경우",
                          value = "{\n  \"code\": 201,\n  \"message\": \"불가능한 요청입니다.\"\n}")
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
  ApiResponse<UserPageResponse> getUserInfos(int size, int page);
}
