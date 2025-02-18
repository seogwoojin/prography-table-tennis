package com.prography.tabletennis.domain.room.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "room")
public class Room extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "room_id")
  Integer id;

  @Column(name = "title")
  String title;

  @Column(name = "host", nullable = false)
  Integer host;

  @Enumerated(EnumType.STRING)
  @Column(name = "room_type", nullable = false)
  RoomType roomType;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  RoomStatus roomStatus;

  @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, orphanRemoval = true)
  List<UserRoom> userRoomList = new ArrayList<>();

  @Builder
  public Room(String title, Integer host, RoomType roomType, RoomStatus roomStatus) {
    this.title = title;
    this.host = host;
    this.roomType = roomType;
    this.roomStatus = roomStatus;
  }

  public void updateRoomStatus(RoomStatus roomStatus) {
    this.roomStatus = roomStatus;
  }

  public boolean isFull() {
    if (this.roomType == RoomType.SINGLE
        && this.userRoomList.size() == this.roomType.getCapacity()) {
      return true;
    } else if (this.roomType == RoomType.DOUBLE
        && this.userRoomList.size() >= this.roomType.getCapacity()) {
      return true;
    }
    return false;
  }
}
