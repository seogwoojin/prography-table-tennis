package com.prography.tabletennis.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
import com.prography.tabletennis.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "users")
public class User extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  Integer id;

  @Column(name = "faker_id")
  Integer fakerId;

  @Column(name = "name")
  String name;

  @Column(name = "email")
  String email;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  UserStatus userStatus;

  // OneToOne 관계에서 주인이 아닌 엔티티는 FetchType.LAZY로 동작하지 않는다.
  @OneToOne(mappedBy = "user") // FetchType.LAZY
  UserRoom userRoom;

  @Builder
  public User(Integer fakerId, String name, String email, UserStatus userStatus) {
    this.fakerId = fakerId;
    this.name = name;
    this.email = email;
    this.userStatus = userStatus;
  }

  public void exitRoom() {
    this.userRoom = null;
  }
}
