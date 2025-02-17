package com.prography.tabletennis.domain.room.entity.enums;

import lombok.Getter;

@Getter
public enum RoomType {
  SINGLE(2),
  DOUBLE(4);

  final Integer capacity;

  RoomType(Integer capacity) {
    this.capacity = capacity;
  }
}
