package com.prography.tabletennis.domain.room.entity;

import jakarta.persistence.*;

import com.prography.tabletennis.domain.room.entity.enums.TeamType;
import com.prography.tabletennis.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "user_room")
public class UserRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  Room room;

  @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
  @OneToOne(fetch = FetchType.LAZY)
  User user;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "team")
  TeamType teamType;

  @Builder
  public UserRoom(Room room, User user, TeamType teamType) {
    this.room = room;
    this.user = user;
    this.teamType = teamType;
  }

  public void changeTeam() {
    if (this.teamType == TeamType.BLUE) {
      this.teamType = TeamType.RED;
      return;
    }
    this.teamType = TeamType.BLUE;
  }
}
