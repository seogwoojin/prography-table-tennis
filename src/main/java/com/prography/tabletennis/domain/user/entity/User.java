package com.prography.tabletennis.domain.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
import com.prography.tabletennis.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer fakerId;
    String name;
    String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    UserStatus userStatus;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    UserRoom userRoom;

    @Builder
    public User(int fakerId, String name, String email, UserStatus userStatus) {
        this.fakerId = fakerId;
        this.name = name;
        this.email = email;
        this.userStatus = userStatus;
    }

    public void addUserRoom(Room room) {
        // Cascade 설정에 의해 자동으로 저장됨
        UserRoom newUserRoom = UserRoom.builder().user(this).room(room).build();
        this.userRoom = newUserRoom;
    }

    public void removeUserRoom() {
        this.userRoom = null;
    }
}
