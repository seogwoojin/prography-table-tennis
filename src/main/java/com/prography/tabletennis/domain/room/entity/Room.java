package com.prography.tabletennis.domain.room.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "room")
public class Room extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "room_id")
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "host", nullable = false)
  private Integer host;

  @Enumerated(EnumType.STRING)
  @Column(name = "room_type", nullable = false)
  private RoomType roomType;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private RoomStatus roomStatus;

  @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, orphanRemoval = true)
  private List<UserRoom> userRoomList = new ArrayList<>();

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
    return this.roomType.isFull(this.userRoomList.size());
  }
}
