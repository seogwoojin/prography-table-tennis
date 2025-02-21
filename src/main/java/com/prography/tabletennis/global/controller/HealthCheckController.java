package com.prography.tabletennis.global.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.tabletennis.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "헬스 체크 API")
public class HealthCheckController {
  @GetMapping
  @Operation(
      summary = "서버 체크 API",
      description = "서버 상태를 체크합니다.",
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
  public ApiResponse<Void> checkServerHealth() {
    return ApiResponse.success();
  }
}
