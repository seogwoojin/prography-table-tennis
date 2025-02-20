package com.prography.tabletennis.domain.room.entity.enums;

public enum TeamType {
  RED,
  BLUE;

  public TeamType getOppositeTeam() {
    if (this == TeamType.RED) {
      return TeamType.BLUE;
    }
    return TeamType.RED;
  }
}
