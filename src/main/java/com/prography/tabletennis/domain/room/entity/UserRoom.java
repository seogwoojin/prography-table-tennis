package com.prography.tabletennis.domain.room.entity;

import jakarta.persistence.*;

import com.prography.tabletennis.domain.room.entity.enums.TeamType;
import com.prography.tabletennis.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_room")
public class UserRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Room room;

  @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
  @OneToOne(fetch = FetchType.LAZY)
  private User user;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "team")
  private TeamType teamType;

  @Builder
  public UserRoom(Room room, User user, TeamType teamType) {
    this.room = room;
    this.user = user;
    this.teamType = teamType;
  }

  public void changeTeam() {
    this.teamType = this.teamType.changeTeam();
  }
}
