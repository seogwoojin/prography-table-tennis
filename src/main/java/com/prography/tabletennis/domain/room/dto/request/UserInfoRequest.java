package com.prography.tabletennis.domain.room.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoRequest {
  @Schema(name = "userId", example = "1")
  private Integer userId;
}
