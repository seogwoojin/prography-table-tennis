package com.prography.tabletennis.domain.room.utils;

import org.springframework.stereotype.Component;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

import lombok.RequiredArgsConstructor;

/** 유저와 방에 관련한 검증만을 담당하는 클래스 */
@Component
@RequiredArgsConstructor
public class RoomValidator {
  private final TeamAssignmentService teamAssignmentService;

  /** 유저가 방을 생성할 조건을 만족하는 지 검증하는 함수 */
  public void validateUserCanCreateRoom(User user) {
    if (user.getUserStatus() != UserStatus.ACTIVE || user.getUserRoom() != null) {
      throw new CustomException(ReturnCode.WRONG_REQUEST);
    }
  }

  /** 유저가 팀에 들어갈 조건을 만족하는 지 검증하는 함수 */
  public void validateUserCanJoinRoom(User user, Room room) {
    validateRoomStatusIsWait(room);
    validateUserCanCreateRoom(user);
    validateRoomIsNotFull(room);
  }

  /** 유저가 팀을 나갈 조건을 만족하는 지 검증하는 함수 */
  public void validateUserCanExitRoom(User user, Room room) {
    validateUserInRoom(user, room);
    validateRoomStatusIsWait(room);
  }

  /** 유저가 게임을 시작할 조건을 만족하는 지 검증하는 함수 */
  public void validateStartGame(User user, Room room) {
    if (!isUserRoomHost(user, room)) {
      throw new CustomException(ReturnCode.WRONG_REQUEST);
    }
    validateRoomStatusIsWait(room);
    if (!teamAssignmentService.isEachTeamFull(room)) {
      throw new CustomException(ReturnCode.WRONG_REQUEST);
    }
  }

  /** 유저가 팀을 변경할 조건을 만족하는 지 검증하는 함수 */
  public void validateChangeTeam(User user, Room room) {
    validateRoomStatusIsWait(room);
    validateUserInRoom(user, room);
    if (!teamAssignmentService.changeTeamPossible(room, user.getUserRoom().getTeamType())) {
      throw new CustomException(ReturnCode.WRONG_REQUEST);
    }
  }

  public boolean isUserRoomHost(User user, Room room) {
    return room.getHost().equals(user.getId());
  }

  /** 방이 정원(Single 2명, Double 4명)을 초과했는지 검사 */
  private void validateRoomIsNotFull(Room room) {
    if (room.isFull()) {
      throw new CustomException(ReturnCode.WRONG_REQUEST);
    }
  }

  private void validateRoomStatusIsWait(Room room) {
    if (room.getRoomStatus() != RoomStatus.WAIT) {
      throw new CustomException(ReturnCode.WRONG_REQUEST);
    }
  }

  private void validateUserInRoom(User user, Room room) {
    UserRoom userRoom = user.getUserRoom();
    if (!room.getUserRoomList().contains(userRoom)) {
      throw new CustomException(ReturnCode.WRONG_REQUEST);
    }
  }
}
