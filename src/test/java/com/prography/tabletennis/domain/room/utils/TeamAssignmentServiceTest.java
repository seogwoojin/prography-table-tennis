package com.prography.tabletennis.domain.room.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

  /** RoomType의 capacity에 따라 팀 당 최대 인원은 capacity / 2 입니다. 예를 들어, capacity가 4이면 팀 당 최대 인원은 2명입니다. */
  @Test
  void testIsEachTeamFull_NotFull() {
    // capacity 4 -> 각 팀 최대 인원은 2
    Room room = Mockito.mock(Room.class);
    RoomType roomType = Mockito.mock(RoomType.class);
    when(roomType.getCapacity()).thenReturn(4);
    when(room.getRoomType()).thenReturn(roomType);

    // 1명의 RED와 1명의 BLUE가 있는 경우 -> full 아님
    UserRoom redUser = Mockito.mock(UserRoom.class);
    when(redUser.getTeamType()).thenReturn(TeamType.RED);
    UserRoom blueUser = Mockito.mock(UserRoom.class);
    when(blueUser.getTeamType()).thenReturn(TeamType.BLUE);
    List<UserRoom> userRooms = Arrays.asList(redUser, blueUser);
    when(room.getUserRoomList()).thenReturn(userRooms);

    boolean isFull = teamAssignmentService.isEachTeamFull(room);
    assertFalse(isFull, "팀이 꽉 차지 않았으므로 false여야 합니다.");
  }

  @Test
  void testIsEachTeamFull_Full() {
    // capacity 4 -> 각 팀 최대 인원은 2
    Room room = Mockito.mock(Room.class);
    RoomType roomType = Mockito.mock(RoomType.class);
    when(roomType.getCapacity()).thenReturn(4);
    when(room.getRoomType()).thenReturn(roomType);

    // RED 2명, BLUE 2명 -> full
    UserRoom red1 = Mockito.mock(UserRoom.class);
    when(red1.getTeamType()).thenReturn(TeamType.RED);
    UserRoom red2 = Mockito.mock(UserRoom.class);
    when(red2.getTeamType()).thenReturn(TeamType.RED);
    UserRoom blue1 = Mockito.mock(UserRoom.class);
    when(blue1.getTeamType()).thenReturn(TeamType.BLUE);
    UserRoom blue2 = Mockito.mock(UserRoom.class);
    when(blue2.getTeamType()).thenReturn(TeamType.BLUE);
    List<UserRoom> userRooms = Arrays.asList(red1, red2, blue1, blue2);
    when(room.getUserRoomList()).thenReturn(userRooms);

    boolean isFull = teamAssignmentService.isEachTeamFull(room);
    assertTrue(isFull, "각 팀이 최대 인원에 도달했으므로 true여야 합니다.");
  }

  @Test
  void testAssignTeam_BothAvailable() {
    // capacity 4 -> 각 팀 최대 인원은 2
    Room room = Mockito.mock(Room.class);
    RoomType roomType = Mockito.mock(RoomType.class);
    when(roomType.getCapacity()).thenReturn(4);
    when(room.getRoomType()).thenReturn(roomType);

    // RED 1명만 존재하면 두 팀 모두 여유가 있으므로 기본값인 RED 할당
    UserRoom redUser = Mockito.mock(UserRoom.class);
    when(redUser.getTeamType()).thenReturn(TeamType.RED);
    List<UserRoom> userRooms = Arrays.asList(redUser);
    when(room.getUserRoomList()).thenReturn(userRooms);

    TeamType assigned = teamAssignmentService.assignTeam(room);
    assertEquals(TeamType.RED, assigned, "여유가 있는 경우 기본적으로 RED팀이 할당되어야 합니다.");
  }

  @Test
  void testAssignTeam_RedFull() {
    // capacity 4 -> 각 팀 최대 인원은 2
    Room room = Mockito.mock(Room.class);
    RoomType roomType = Mockito.mock(RoomType.class);
    when(roomType.getCapacity()).thenReturn(4);
    when(room.getRoomType()).thenReturn(roomType);

    // RED팀에 이미 2명(최대 인원), BLUE팀은 1명 -> BLUE 할당되어야 함
    UserRoom red1 = Mockito.mock(UserRoom.class);
    when(red1.getTeamType()).thenReturn(TeamType.RED);
    UserRoom red2 = Mockito.mock(UserRoom.class);
    when(red2.getTeamType()).thenReturn(TeamType.RED);
    UserRoom blueUser = Mockito.mock(UserRoom.class);
    when(blueUser.getTeamType()).thenReturn(TeamType.BLUE);
    List<UserRoom> userRooms = Arrays.asList(red1, red2, blueUser);
    when(room.getUserRoomList()).thenReturn(userRooms);

    TeamType assigned = teamAssignmentService.assignTeam(room);
    assertEquals(TeamType.BLUE, assigned, "RED팀이 꽉 찼으므로 BLUE팀이 할당되어야 합니다.");
  }

  @Test
  void testAssignTeam_BlueFull() {
    // capacity 4 -> 각 팀 최대 인원은 2
    Room room = Mockito.mock(Room.class);
    RoomType roomType = Mockito.mock(RoomType.class);
    when(roomType.getCapacity()).thenReturn(4);
    when(room.getRoomType()).thenReturn(roomType);

    // BLUE팀에 이미 2명(최대 인원), RED팀은 1명 -> RED 할당되어야 함
    UserRoom blue1 = Mockito.mock(UserRoom.class);
    when(blue1.getTeamType()).thenReturn(TeamType.BLUE);
    UserRoom blue2 = Mockito.mock(UserRoom.class);
    when(blue2.getTeamType()).thenReturn(TeamType.BLUE);
    UserRoom redUser = Mockito.mock(UserRoom.class);
    when(redUser.getTeamType()).thenReturn(TeamType.RED);
    List<UserRoom> userRooms = Arrays.asList(blue1, blue2, redUser);
    when(room.getUserRoomList()).thenReturn(userRooms);

    TeamType assigned = teamAssignmentService.assignTeam(room);
    assertEquals(TeamType.RED, assigned, "BLUE팀이 꽉 찼으므로 RED팀이 할당되어야 합니다.");
  }

  @Test
  void testAssignTeam_BothFull_ThrowsException() {
    // capacity 4 -> 각 팀 최대 인원은 2
    Room room = Mockito.mock(Room.class);
    RoomType roomType = Mockito.mock(RoomType.class);
    when(roomType.getCapacity()).thenReturn(4);
    when(room.getRoomType()).thenReturn(roomType);

    // 두 팀 모두 꽉 찬 경우 (RED 2명, BLUE 2명)
    UserRoom red1 = Mockito.mock(UserRoom.class);
    when(red1.getTeamType()).thenReturn(TeamType.RED);
    UserRoom red2 = Mockito.mock(UserRoom.class);
    when(red2.getTeamType()).thenReturn(TeamType.RED);
    UserRoom blue1 = Mockito.mock(UserRoom.class);
    when(blue1.getTeamType()).thenReturn(TeamType.BLUE);
    UserRoom blue2 = Mockito.mock(UserRoom.class);
    when(blue2.getTeamType()).thenReturn(TeamType.BLUE);
    List<UserRoom> userRooms = Arrays.asList(red1, red2, blue1, blue2);
    when(room.getUserRoomList()).thenReturn(userRooms);

    // assignTeam() 호출 시 CustomException이 발생해야 함
    assertThrows(CustomException.class, () -> teamAssignmentService.assignTeam(room));
  }
}
