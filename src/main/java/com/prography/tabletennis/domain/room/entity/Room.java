package com.prography.tabletennis.domain.room.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.prography.tabletennis.domain.user.entity.UserRoom;
import com.prography.tabletennis.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "room")
    List<UserRoom> userRoomList;

    public void updateRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public boolean isFull() {
        if (this.roomType == RoomType.SINGLE && this.userRoomList.size() >= RoomType.SINGLE.value) {
            return true;
        } else if (this.roomType == RoomType.DOUBLE
                && this.userRoomList.size() >= RoomType.DOUBLE.value) {
            return true;
        }
        return false;
    }
}
