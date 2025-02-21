package com.prography.tabletennis.domain.room.controller;

import org.springframework.web.bind.annotation.*;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.dto.request.UserInfoRequest;
import com.prography.tabletennis.domain.room.dto.response.RoomDetailInfoResponse;
import com.prography.tabletennis.domain.room.dto.response.RoomPageResponse;
import com.prography.tabletennis.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Room API", description = "게임방 관련 API")
public interface RoomController {

  @Operation(
      summary = "방 생성",
      description = "새로운 방을 생성합니다.",
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
                          description = "새로운 방이 생성된 경우",
                          value = "{\n  \"code\": 200,\n  \"message\": \"API 요청이 성공했습니다.\"\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "잘못된 API 호출인 경우",
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
  ApiResponse<Void> createRoom(CreateRoomRequest createRoomRequest);

  @Operation(
      summary = "방 목록 조회",
      description = "페이징 처리된 방 정보를 조회합니다.",
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
                          description = "방 목록이 조회된 경우",
                          value =
                              "{\n  \"totalElements\": 2,\n  \"totalPages\": 1,\n  \"roomList\": [\n    {\n      \"id\": 1,\n      \"title\": \"Room Title 1\",\n      \"hostId\": 10,\n      \"roomType\": \"SINGLE\",\n      \"status\": \"WAIT\"\n    },\n    {\n      \"id\": 2,\n      \"title\": \"Room Title 2\",\n      \"hostId\": 20,\n      \"roomType\": \"DOUBLE\",\n      \"status\": \"ACTIVE\"\n    }\n  ]\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "잘못된 API 호출인 경우",
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
  ApiResponse<RoomPageResponse> getRoomInfos(int size, int page);

  @Operation(
      summary = "방 상세 조회",
      description = "Room Id에 해당하는 방의 세부정보를 응답합니다.",
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
                          description = "응답이 성공한 경우",
                          value =
                              "{\n  \"code\": 200,\n  \"message\": \"API 요청이 성공했습니다.\",\n  \"result\": {\n    \"id\": 1,\n    \"title\": \"Room Title\",\n    \"hostId\": 10,\n    \"roomType\": \"SINGLE\",\n    \"status\": \"WAIT\",\n    \"createdAt\": \"2025-02-18 12:00:00\",\n    \"updatedAt\": \"2025-02-18 12:00:00\"\n  }\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "잘못된 API 호출인 경우",
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
  ApiResponse<RoomDetailInfoResponse> getRoomDetail(Integer roomId);

  @Operation(
      summary = "방 참여",
      description = "사용자가 방에 참여합니다.",
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
                          description = "방 참여에 성공한 경우",
                          value = "{\n  \"code\": 200,\n  \"message\": \"API 요청이 성공했습니다.\"\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "잘못된 API 호출인 경우",
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
  ApiResponse<Void> joinRoom(Integer roomId, UserInfoRequest userInfoRequest);

  @Operation(
      summary = "방 나가기",
      description = "사용자가 방에서 나갑니다.",
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
                          description = "방 나가기에 성공한 경우",
                          value = "{\n  \"code\": 200,\n  \"message\": \"API 요청이 성공했습니다.\"\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "잘못된 API 호출인 경우",
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
  ApiResponse<Void> exitRoom(Integer roomId, UserInfoRequest userInfoRequest);

  @Operation(
      summary = "게임 시작",
      description = "방에서 게임을 시작합니다.",
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
                          description = "게임 시작에 성공한 경우",
                          value = "{\n  \"code\": 200,\n  \"message\": \"API 요청이 성공했습니다.\"\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "잘못된 API 호출인 경우",
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
  ApiResponse<Void> startGame(Integer roomId, UserInfoRequest userInfoRequest);
}
