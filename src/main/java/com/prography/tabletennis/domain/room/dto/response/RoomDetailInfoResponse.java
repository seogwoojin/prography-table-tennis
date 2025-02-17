package com.prography.tabletennis.domain.room.dto.response;

import static com.prography.tabletennis.global.utils.GlobalConstant.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomDetailInfoResponse {
  Integer id;
  String title;
  Integer hostId;
  RoomType roomType;

  @JsonProperty("status")
  RoomStatus roomStatus;

  String createdAt;
  String updatedAt;

  @Builder
  public RoomDetailInfoResponse(
      Integer id,
      String title,
      Integer hostId,
      RoomType roomType,
      RoomStatus roomStatus,
      String createdAt,
      String updatedAt) {
    this.id = id;
    this.title = title;
    this.hostId = hostId;
    this.roomType = roomType;
    this.roomStatus = roomStatus;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static RoomDetailInfoResponse from(Room room) {
    return RoomDetailInfoResponse.builder()
        .id(room.getId())
        .title(room.getTitle())
        .hostId(room.getHost())
        .roomType(room.getRoomType())
        .roomStatus(room.getRoomStatus())
        .createdAt(room.getCreatedAt().format(DATE_TIME_FORMATTER))
        .updatedAt(room.getUpdatedAt().format(DATE_TIME_FORMATTER))
        .build();
  }
}
