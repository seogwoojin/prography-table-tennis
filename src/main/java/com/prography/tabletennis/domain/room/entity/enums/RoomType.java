package com.prography.tabletennis.domain.room.entity.enums;

import lombok.Getter;

@Getter
public enum RoomType {
  SINGLE(2),
  DOUBLE(4);

  final int capacity;

  RoomType(int capacity) {
    this.capacity = capacity;
  }

  public boolean isFull(int userCount) {
    return userCount >= capacity;
  }
}
