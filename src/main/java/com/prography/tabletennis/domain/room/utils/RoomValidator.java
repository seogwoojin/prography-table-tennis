package com.prography.tabletennis.domain.room.utils;

import org.springframework.stereotype.Component;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.RoomStatus;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.UserRoom;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

/** 유저와 방에 관련한 검증만을 담당하는 클래스 */
@Component
public class RoomValidator {
    /** 사용자가 방 참여 가능한지(활성화 상태 + 다른 방에 속해있지 않은지) 검증 */
    public void validateUserIsEligibleForRoom(User user) {
        if (user.getUserStatus() != UserStatus.ACTIVE || user.getUserRoom() != null) {
            throw new CustomException(ReturnCode.WRONG_REQUEST);
        }
    }

    /** 방 참여 시 추가로 필요한 검증 로직 */
    public void validateUserCanJoinRoom(User user, Room room) {
        validateRoomStatus(room);
        validateUserIsEligibleForRoom(user);
        validateRoomIsNotFull(room);
    }

    /** 방이 정원(예: 2명)을 초과했는지 검사 */
    private void validateRoomIsNotFull(Room room) {
        if (room.isFull()) {
            throw new CustomException(ReturnCode.WRONG_REQUEST);
        }
    }

    public void validateUserCanExitRoom(User user, Room room) {
        validateUserRoomSame(user, room);
        validateRoomStatus(room);
    }

    public boolean isUserRoomHost(User user, Room room) {
        return room.getHost().equals(user.getId());
    }

    private void validateRoomStatus(Room room) {
        if (room.getRoomStatus() != RoomStatus.WAIT) {
            throw new CustomException(ReturnCode.WRONG_REQUEST);
        }
    }

    private void validateUserRoomSame(User user, Room room) {
        UserRoom userRoom = user.getUserRoom();
        System.out.println("userRoom = " + !room.getUserRoomList().contains(userRoom));
        if (!room.getUserRoomList().contains(userRoom)) {
            throw new CustomException(ReturnCode.WRONG_REQUEST);
        }
    }
}
