package com.prography.tabletennis.domain.room.entity;

public enum RoomType {
    SINGLE(2),
    DOUBLE(4);

    final Integer value;

    RoomType(Integer value) {
        this.value = value;
    }
}
