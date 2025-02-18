package com.prography.tabletennis.domain.room.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.domain.room.entity.enums.TeamType;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

@Component
public class TeamAssignmentService {
  public boolean isEachTeamFull(Room room) {
    List<UserRoom> userRooms = room.getUserRoomList();
    Map<TeamType, Long> teamCounts = countTeamMembers(userRooms);
    int maxPerTeam = calculateMaxPerTeam(room.getRoomType());
    return compareMaxPerTeamWithCounts(teamCounts, maxPerTeam);
  }

  private boolean compareMaxPerTeamWithCounts(Map<TeamType, Long> teamCounts, int maxPerTeam) {
    long redCount = teamCounts.getOrDefault(TeamType.RED, 0L);
    long blueCount = teamCounts.getOrDefault(TeamType.BLUE, 0L);
    return redCount == maxPerTeam && blueCount == maxPerTeam;
  }

  public TeamType assignTeam(Room room) {
    List<UserRoom> userRooms = room.getUserRoomList();
    Map<TeamType, Long> teamCounts = countTeamMembers(userRooms);
    int maxPerTeam = calculateMaxPerTeam(room.getRoomType());
    return selectTeam(teamCounts, maxPerTeam);
  }

  /** 팀별 인원 수를 계산합니다. */
  private Map<TeamType, Long> countTeamMembers(List<UserRoom> userRooms) {
    return userRooms.stream()
        .collect(Collectors.groupingBy(UserRoom::getTeamType, Collectors.counting()));
  }

  /** RoomType에 따른 팀당 최대 인원 수를 계산 */
  private int calculateMaxPerTeam(RoomType roomType) {
    return roomType.getCapacity() / 2;
  }

  /** 현재 팀 인원과 최대 인원을 고려하여 들어갈 수 있는 팀을 선택 */
  private TeamType selectTeam(Map<TeamType, Long> teamCounts, int maxPerTeam) {
    long redCount = teamCounts.getOrDefault(TeamType.RED, 0L);
    long blueCount = teamCounts.getOrDefault(TeamType.BLUE, 0L);

    // 두 팀 모두 여유가 있는 경우 Red 팀 선택
    if (redCount < maxPerTeam && blueCount < maxPerTeam) {
      return TeamType.RED;
    } else if (redCount < maxPerTeam) {
      return TeamType.RED;
    } else if (blueCount < maxPerTeam) {
      return TeamType.BLUE;
    }
    throw new CustomException(ReturnCode.WRONG_REQUEST);
  }
}
