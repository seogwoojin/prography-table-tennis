package com.prography.tabletennis.domain.room.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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
@Entity
public class Room extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  String title;
  Integer host;

  @Enumerated(EnumType.STRING)
  RoomType roomType;

  @Enumerated(EnumType.STRING)
  RoomStatus roomStatus;

  @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, orphanRemoval = true)
  List<UserRoom> userRoomList = new ArrayList<>();

  @Builder
  public Room(
      String title,
      Integer host,
      RoomType roomType,
      RoomStatus roomStatus,
      List<UserRoom> userRoomList) {
    this.title = title;
    this.host = host;
    this.roomType = roomType;
    this.roomStatus = roomStatus;
    this.userRoomList = userRoomList;
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
