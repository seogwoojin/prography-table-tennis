package com.prography.tabletennis.domain.room.dto.request;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateRoomRequest {
  private Integer userId;
  private RoomType roomType;
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
