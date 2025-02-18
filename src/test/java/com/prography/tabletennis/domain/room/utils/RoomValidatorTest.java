package com.prography.tabletennis.domain.room.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

@ExtendWith(MockitoExtension.class)
class RoomValidatorTest {

  @Mock private TeamAssignmentService teamAssignmentService;

  @InjectMocks private RoomValidator roomValidator;

  @Test
  @DisplayName("유저 방 생성 자격 - 적합한 경우")
  void validateUserIsEligibleForRoom_validUser_doesNotThrow() {
    // given
    User user = mock(User.class);
    when(user.getUserStatus()).thenReturn(UserStatus.ACTIVE);
    when(user.getUserRoom()).thenReturn(null);

    // when & then
    roomValidator.validateUserIsCanCreateRoom(user);
  }

  @Test
  @DisplayName("유저 방 생성 자격 부적절 - 유저 상태가 ACTIVE가 아님")
  void validateUserIsEligibleForRoom_inactiveUser_throwsException() {
    // given
    User user = mock(User.class);
    when(user.getUserStatus()).thenReturn(UserStatus.WAIT);

    // then
    assertThatThrownBy(() -> roomValidator.validateUserIsCanCreateRoom(user))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("유저 방 생성 자격 부적절 - 유저가 이미 속한 방이 있음")
  void validateUserIsEligibleForRoom_userAlreadyInRoom_throwsException() {
    // given
    User user = mock(User.class);
    when(user.getUserStatus()).thenReturn(UserStatus.ACTIVE);
    when(user.getUserRoom()).thenReturn(mock(UserRoom.class));
    // then
    assertThatThrownBy(() -> roomValidator.validateUserIsCanCreateRoom(user))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("유저 팀 참가 가능 확인 - 조건을 모두 만족하는 경우")
  void validateUserCanJoinRoom_valid_doesNotThrow() {
    // given
    User user = User.builder().userStatus(UserStatus.ACTIVE).build();
    Room room = Room.builder().roomStatus(RoomStatus.WAIT).roomType(RoomType.SINGLE).build();
    Room spyRoom = spy(room);
    doReturn(false).when(spyRoom).isFull();

    // when & then
    roomValidator.validateUserCanJoinRoom(user, spyRoom);
    assertThat(spyRoom.getRoomStatus()).isEqualTo(RoomStatus.WAIT);
  }

  @Test
  @DisplayName("유저 팀 참가 예외 - 방의 정원이 모두 찬 경우")
  void validateUserCanJoinRoom_roomFull_throwsException() {
    // given
    User user = User.builder().userStatus(UserStatus.ACTIVE).build();
    Room room = Room.builder().roomStatus(RoomStatus.WAIT).roomType(RoomType.SINGLE).build();
    Room spyRoom = spy(room);
    doReturn(true).when(spyRoom).isFull();

    // when, then
    assertThatThrownBy(() -> roomValidator.validateUserCanJoinRoom(user, spyRoom))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("유저 팀 나가기 가능한 지 확인 - 조건을 모두 만족하는 경우")
  void validateUserCanExitRoom_valid_doesNotThrow() {
    // given
    User user = User.builder().userStatus(UserStatus.ACTIVE).build();
    User spyUser = spy(user);
    Room room =
        Room.builder()
            .roomStatus(RoomStatus.WAIT)
            .roomType(RoomType.SINGLE)
            .host(user.getId())
            .build();
    Room spyRoom = spy(room);

    UserRoom userRoom = mock(UserRoom.class);
    doReturn(userRoom).when(spyUser).getUserRoom();
    doReturn(List.of(userRoom)).when(spyRoom).getUserRoomList();

    // when & then
    roomValidator.validateUserCanExitRoom(spyUser, spyRoom);
    assertThat(room.getRoomStatus()).isEqualTo(RoomStatus.WAIT);
  }

  @Test
  @DisplayName("유저 팀 나가기 가능한 지 확인 - 유저가 해당 방에 없는 경우 - 예외")
  void validateUserCanExitRoom_userNotInRoom_throwsException() {
    // given
    User user = User.builder().userStatus(UserStatus.ACTIVE).build();
    User spyUser = spy(user);
    Room room =
        Room.builder()
            .roomStatus(RoomStatus.WAIT)
            .roomType(RoomType.SINGLE)
            .host(user.getId())
            .build();
    Room spyRoom = spy(room);

    UserRoom userRoom = mock(UserRoom.class);
    doReturn(userRoom).when(spyUser).getUserRoom();
    doReturn(List.of()).when(spyRoom).getUserRoomList();

    // then
    assertThatThrownBy(() -> roomValidator.validateUserCanExitRoom(spyUser, spyRoom))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("유저 Host 검증 함수 테스트 - True")
  void isUserRoomHost_returnsTrueWhenUserIsHost() {
    // given
    User user = mock(User.class);
    Room room = mock(Room.class);
    when(user.getId()).thenReturn(1);
    when(room.getHost()).thenReturn(1);

    // when
    boolean result = roomValidator.isUserRoomHost(user, room);

    // then
    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("유저 Host 검증 함수 테스트 - False")
  void isUserRoomHost_returnsFalseWhenUserIsNotHost() {
    // given
    User user = mock(User.class);
    Room room = mock(Room.class);
    when(user.getId()).thenReturn(2);
    when(room.getHost()).thenReturn(1);

    // when
    boolean result = roomValidator.isUserRoomHost(user, room);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("게임 시작 조건 - 모든 조건 만족 시")
  void validateStartGame_valid_doesNotThrow() {
    // given
    User user = mock(User.class);
    Room room = mock(Room.class);
    when(user.getId()).thenReturn(1);
    when(room.getHost()).thenReturn(1);
    when(room.getRoomStatus()).thenReturn(RoomStatus.WAIT);

    when(teamAssignmentService.isEachTeamFull(room)).thenReturn(true);

    // when & then
    roomValidator.validateStartGame(user, room);
  }

  @Test
  @DisplayName("게임 시작 조건 불만족 - 방 상태 부적절")
  void validateStartGame_roomFinished_throwsException() {
    // given
    User user = mock(User.class);
    Room room = mock(Room.class);
    when(user.getId()).thenReturn(1);
    when(room.getHost()).thenReturn(1);
    when(room.getRoomStatus()).thenReturn(RoomStatus.FINISH);

    // when & then
    assertThatThrownBy(() -> roomValidator.validateStartGame(user, room))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("게임 시작 조건 불만족 - 팀 인원 부족")
  void validateStartGame_teamNotFull_throwsException() {
    // given
    User user = mock(User.class);
    Room room = mock(Room.class);
    when(user.getId()).thenReturn(1);
    when(room.getHost()).thenReturn(1);
    when(room.getRoomStatus()).thenReturn(RoomStatus.FINISH);

    // when & then
    assertThatThrownBy(() -> roomValidator.validateStartGame(user, room))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }
}
