package com.prography.tabletennis.domain.Initialization.controller;

import com.prography.tabletennis.domain.Initialization.dto.request.InitDataRequest;
import com.prography.tabletennis.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Init API", description = "DB 초기화 관련 API")
public interface InitializationController {
  @Operation(
      summary = "DB 초기화",
      description = "Table의 모든 데이터를 삭제하고, Seed, Quantity에 맞는 회원 정보를 저장",
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
                          description = "초기화가 완료된 경우",
                          value = "{\n  \"code\": 200,\n  \"message\": \"API 요청이 성공했습니다.\"\n}"),
                      @ExampleObject(
                          name = "WRONG_REQUEST",
                          description = "잘못된 API 호출인 경우",
                          value = "{\n  \"code\": 201,\n  \"message\": \"불가능한 요청입니다.\"\n}"),
                      @ExampleObject(
                          name = "SERVER_ERROR",
                          description = "서버 에러가 발생한 경우",
                          value = "{\n  \"code\": 500,\n  \"message\": \"에러가 발생했습니다.\"\n}")
                    }))
      })
  ApiResponse<Void> initializeData(InitDataRequest initDataRequest);
}
