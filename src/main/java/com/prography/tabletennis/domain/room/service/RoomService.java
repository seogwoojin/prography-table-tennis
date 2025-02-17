package com.prography.tabletennis.domain.room.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.dto.request.UserInfoRequest;
import com.prography.tabletennis.domain.room.dto.response.RoomDetailInfoResponse;
import com.prography.tabletennis.domain.room.dto.response.RoomPageResponse;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.RoomStatus;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.room.utils.RoomValidator;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.UserRoom;
import com.prography.tabletennis.domain.user.service.UserService;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserService userService;
    private final RoomValidator roomValidator;

    @Transactional
    public void createNewRoom(CreateRoomRequest createRoomRequest) {
        User user = userService.getUserById(createRoomRequest.getUserId());
        roomValidator.validateUserIsEligibleForRoom(user);
        Room room = roomRepository.save(createRoomRequest.toEntity());
        user.addUserRoom(room);
    }

    @Transactional
    public void joinRoom(Integer roomId, UserInfoRequest userInfoRequest) {
        User user = userService.getUserById(userInfoRequest.getUserId());
        Room room =
                roomRepository
                        .findById(roomId)
                        .orElseThrow(() -> new CustomException(ReturnCode.WRONG_REQUEST));
        roomValidator.validateUserCanJoinRoom(user, room);
        user.addUserRoom(room);
    }

    @Transactional
    public void exitRoom(Integer userId, Integer roomId) {
        User user = userService.getUserById(userId);
        Room room = getRoomById(roomId);
        roomValidator.validateUserCanExitRoom(user, room);

        if (roomValidator.isUserRoomHost(user, room)) {
            room.updateRoomStatus(RoomStatus.FINISH);
            exitAllUsersInRoom(room);
            return;
        }
        user.removeUserRoom();
    }

    private void exitAllUsersInRoom(Room room) {
        List<UserRoom> userRoomList = room.getUserRoomList();
        userRoomList.forEach(userRoom -> userRoom.getUser().removeUserRoom());
    }

    public RoomPageResponse getRoomInfos(PageRequest pageRequest) {
        Page<Room> rooms = roomRepository.findAll(pageRequest);
        return RoomPageResponse.from(rooms);
    }

    public RoomDetailInfoResponse getRoomDetailInfo(Integer roomId) {
        Room room =
                roomRepository
                        .findById(roomId)
                        .orElseThrow(() -> new CustomException(ReturnCode.WRONG_REQUEST));
        return RoomDetailInfoResponse.from(room);
    }

    private Room getRoomById(Integer roomId) {
        return roomRepository
                .findById(roomId)
                .orElseThrow(() -> new CustomException(ReturnCode.WRONG_REQUEST));
    }

    @Transactional
    public void deleteAll() {
        roomRepository.deleteAll();
    }
}
