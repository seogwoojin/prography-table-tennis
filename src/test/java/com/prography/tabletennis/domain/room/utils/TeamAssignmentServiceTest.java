package com.prography.tabletennis.domain.room.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.domain.room.entity.enums.TeamType;
import com.prography.tabletennis.global.response.CustomException;

@SpringBootTest
class TeamAssignmentServiceTest {

  @Autowired private TeamAssignmentService teamAssignmentService;

  // Room과 관련된 공통 생성 로직을 헬퍼 메서드로 분리
  private Room createMockRoom(RoomType roomType, List<UserRoom> userRooms) {
    Room room = Mockito.mock(Room.class);
    when(room.getRoomType()).thenReturn(roomType);
    when(room.getUserRoomList()).thenReturn(userRooms);
    return room;
  }

  // 특정 팀 타입의 UserRoom을 생성하는 헬퍼 메서드
  private UserRoom createUserRoom(TeamType teamType) {
    UserRoom userRoom = Mockito.mock(UserRoom.class);
    when(userRoom.getTeamType()).thenReturn(teamType);
    return userRoom;
  }

  @DisplayName("각 팀 인원이 최대 인원 미만인 경우 팀이 꽉 차지 않음")
  @Test
  void testIsEachTeamFull_NotFull() {
    // given
    List<UserRoom> userRooms = List.of(createUserRoom(TeamType.RED));
    Room room = createMockRoom(RoomType.SINGLE, userRooms);

    // when
    boolean isFull = teamAssignmentService.isEachTeamFull(room);

    // then
    assertFalse(isFull);
  }

  @DisplayName("각 팀 인원이 최대 인원에 도달한 경우 팀이 꽉 참")
  @Test
  void testIsEachTeamFull_Full() {
    // given
    List<UserRoom> userRooms =
        List.of(
            createUserRoom(TeamType.RED),
            createUserRoom(TeamType.RED),
            createUserRoom(TeamType.BLUE),
            createUserRoom(TeamType.BLUE));
    Room room = createMockRoom(RoomType.DOUBLE, userRooms);

    // when
    boolean isFull = teamAssignmentService.isEachTeamFull(room);

    // then
    assertTrue(isFull);
  }

  @DisplayName("두 팀 모두 여유 있는 경우 기본적으로 RED 팀 할당")
  @Test
  void testAssignTeam_BothAvailable() {
    // given
    List<UserRoom> userRooms = List.of(createUserRoom(TeamType.RED));
    Room room = createMockRoom(RoomType.DOUBLE, userRooms);

    // when
    TeamType assigned = teamAssignmentService.assignTeam(room);

    // then
    assertThat(assigned).isEqualTo(TeamType.RED);
  }

  @DisplayName("RED 팀이 꽉 찬 경우 BLUE 팀 할당")
  @Test
  void testAssignTeam_RedFull() {
    // given
    List<UserRoom> userRooms =
        Arrays.asList(
            createUserRoom(TeamType.RED),
            createUserRoom(TeamType.RED),
            createUserRoom(TeamType.BLUE));
    Room room = createMockRoom(RoomType.DOUBLE, userRooms);

    // when
    TeamType assigned = teamAssignmentService.assignTeam(room);

    // then
    assertThat(assigned).isEqualTo(TeamType.BLUE);
  }

  @DisplayName("BLUE 팀이 꽉 찬 경우 RED 팀 할당")
  @Test
  void testAssignTeam_BlueFull() {
    // given
    List<UserRoom> userRooms =
        List.of(
            createUserRoom(TeamType.BLUE),
            createUserRoom(TeamType.BLUE),
            createUserRoom(TeamType.RED));
    Room room = createMockRoom(RoomType.DOUBLE, userRooms);

    // when
    TeamType assigned = teamAssignmentService.assignTeam(room);

    // then
    assertThat(assigned).isEqualTo(TeamType.RED);
  }

  @DisplayName("두 팀 모두 꽉 찬 경우 예외 발생")
  @Test
  void testAssignTeam_BothFull_ThrowsException() {
    // given
    List<UserRoom> userRooms =
        List.of(
            createUserRoom(TeamType.RED),
            createUserRoom(TeamType.RED),
            createUserRoom(TeamType.BLUE),
            createUserRoom(TeamType.BLUE));
    Room room = createMockRoom(RoomType.DOUBLE, userRooms);

    // when, then
    assertThatThrownBy(() -> teamAssignmentService.assignTeam(room))
        .isInstanceOf(CustomException.class);
  }
}
