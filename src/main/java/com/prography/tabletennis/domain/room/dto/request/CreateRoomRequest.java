package com.prography.tabletennis.domain.room.dto.request;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRoomRequest {
  @Schema(name = "userId", example = "1")
  private Integer userId;

  @Schema(name = "roomType", example = "SINGLE")
  private RoomType roomType;

  @Schema(name = "title", example = "새로운 방")
  private String title;

  public Room toEntity() {
    return Room.builder()
        .title(title)
        .roomStatus(RoomStatus.WAIT)
        .roomType(roomType)
        .host(userId)
        .build();
  }
}
