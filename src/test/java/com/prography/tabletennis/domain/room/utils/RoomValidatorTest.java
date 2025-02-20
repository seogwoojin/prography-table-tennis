package com.prography.tabletennis.domain.room.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.domain.room.entity.enums.TeamType;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // lenient()를 사용하여 불필요한 stubbing 경고를 피함
class RoomValidatorTest {

  @Mock private TeamAssignmentService teamAssignmentService;

  @InjectMocks private RoomValidator roomValidator;

  // 헬퍼 메서드: User 목 생성 (userStatus, userRoom 설정)
  private User createUser(UserStatus status, UserRoom userRoom) {
    User user = mock(User.class);
    lenient().when(user.getUserStatus()).thenReturn(status);
    lenient().when(user.getUserRoom()).thenReturn(userRoom);
    return user;
  }

  // 헬퍼 메서드: id까지 설정하는 User 목 생성
  private User createUserWithId(int id, UserStatus status, UserRoom userRoom) {
    User user = createUser(status, userRoom);
    lenient().when(user.getId()).thenReturn(id);
    return user;
  }

  // 헬퍼 메서드: UserRoom 목 생성 (teamType 설정)
  private UserRoom createUserRoom(TeamType teamType) {
    UserRoom userRoom = mock(UserRoom.class);
    lenient().when(userRoom.getTeamType()).thenReturn(teamType);
    return userRoom;
  }

  // 헬퍼 메서드: Room 목 생성 (roomStatus, roomType, host, userRoomList 설정)
  private Room createRoom(RoomStatus status, RoomType type, int host, List<UserRoom> userRooms) {
    Room room = mock(Room.class);
    lenient().when(room.getRoomStatus()).thenReturn(status);
    lenient().when(room.getRoomType()).thenReturn(type);
    lenient().when(room.getHost()).thenReturn(host);
    lenient().when(room.getUserRoomList()).thenReturn(userRooms);
    return room;
  }

  @Test
  @DisplayName("유저 방 생성 자격 - 적합한 경우")
  void validateUserIsEligibleForRoom_validUser_doesNotThrow() {
    // given
    User user = createUser(UserStatus.ACTIVE, null);

    // when & then
    assertThatCode(() -> roomValidator.validateUserCanCreateRoom(user)).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("유저 방 생성 자격 부적절 - 유저 상태가 ACTIVE가 아님")
  void validateUserIsEligibleForRoom_inactiveUser_throwsException() {
    // given
    User user = createUser(UserStatus.WAIT, null);

    // then
    assertThatThrownBy(() -> roomValidator.validateUserCanCreateRoom(user))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("유저 방 생성 자격 부적절 - 유저가 이미 속한 방이 있음")
  void validateUserIsEligibleForRoom_userAlreadyInRoom_throwsException() {
    // given
    User user = createUser(UserStatus.ACTIVE, createUserRoom(TeamType.RED));

    // then
    assertThatThrownBy(() -> roomValidator.validateUserCanCreateRoom(user))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("유저 팀 참가 가능 확인 - 조건을 모두 만족하는 경우")
  void validateUserCanJoinRoom_valid_doesNotThrow() {
    // given
    User user = createUser(UserStatus.ACTIVE, null);
    // spyRoom 생성 시 isFull()이 false 반환하도록 설정
    Room room = createRoom(RoomStatus.WAIT, RoomType.SINGLE, 1, List.of());
    lenient().when(teamAssignmentService.isEachTeamFull(room)).thenReturn(true);

    // when & then
    assertThatCode(() -> roomValidator.validateUserCanJoinRoom(user, room))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("유저 팀 참가 예외 - 방의 정원이 모두 찬 경우")
  void validateUserCanJoinRoom_roomFull_throwsException() {
    // given
    User user = createUser(UserStatus.ACTIVE, null);
    Room room =
        createRoom(
            RoomStatus.WAIT,
            RoomType.SINGLE,
            1,
            List.of(createUserRoom(TeamType.RED), createUserRoom(TeamType.BLUE)));
    when(room.isFull()).thenReturn(true);

    // when, then
    assertThatThrownBy(() -> roomValidator.validateUserCanJoinRoom(user, room))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("유저 팀 나가기 가능한 지 확인 - 조건을 모두 만족하는 경우")
  void validateUserCanExitRoom_valid_doesNotThrow() {
    UserRoom userRoom = createUserRoom(TeamType.RED);
    User user = createUser(UserStatus.ACTIVE, userRoom);
    Room room = createRoom(RoomStatus.WAIT, RoomType.SINGLE, 1, List.of(userRoom));

    // when & then
    assertThatCode(() -> roomValidator.validateUserCanExitRoom(user, room))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("유저 팀 나가기 가능한 지 확인 - 유저가 해당 방에 없는 경우 - 예외")
  void validateUserCanExitRoom_userNotInRoom_throwsException() {
    // given
    UserRoom userRoom = createUserRoom(TeamType.RED);
    User user = createUser(UserStatus.ACTIVE, createUserRoom(TeamType.RED));
    Room room = createRoom(RoomStatus.WAIT, RoomType.SINGLE, 1, List.of(userRoom));

    // then
    assertThatThrownBy(() -> roomValidator.validateUserCanExitRoom(user, room))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("유저 Host 검증 함수 테스트 - True")
  void isUserRoomHost_returnsTrueWhenUserIsHost() {
    // given
    User user = createUserWithId(1, UserStatus.ACTIVE, null);
    Room room = createRoom(RoomStatus.WAIT, null, 1, List.of());

    // when
    boolean result = roomValidator.isUserRoomHost(user, room);

    // then
    assertTrue(result);
  }

  @Test
  @DisplayName("유저 Host 검증 함수 테스트 - False")
  void isUserRoomHost_returnsFalseWhenUserIsNotHost() {
    // given
    User user = createUserWithId(2, UserStatus.ACTIVE, null);
    Room room = createRoom(RoomStatus.WAIT, null, 1, List.of());

    // when
    boolean result = roomValidator.isUserRoomHost(user, room);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("게임 시작 조건 - 모든 조건 만족 시")
  void validateStartGame_valid_doesNotThrow() {
    // given
    User user = createUserWithId(1, UserStatus.ACTIVE, null);
    Room room = createRoom(RoomStatus.WAIT, null, 1, List.of());
    lenient().when(teamAssignmentService.isEachTeamFull(room)).thenReturn(true);

    // when & then
    assertThatCode(() -> roomValidator.validateStartGame(user, room)).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("게임 시작 조건 불만족 - 방 상태 부적절")
  void validateStartGame_roomFinished_throwsException() {
    // given
    User user = createUserWithId(1, UserStatus.ACTIVE, null);
    Room room = createRoom(RoomStatus.FINISH, null, 1, List.of());

    // when & then
    assertThatThrownBy(() -> roomValidator.validateStartGame(user, room))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }

  @Test
  @DisplayName("게임 시작 조건 불만족 - 팀 인원 부족")
  void validateStartGame_teamNotFull_throwsException() {
    // given
    User user = createUserWithId(1, UserStatus.ACTIVE, null);
    Room room = createRoom(RoomStatus.FINISH, null, 1, List.of());

    // when & then
    assertThatThrownBy(() -> roomValidator.validateStartGame(user, room))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }
}
