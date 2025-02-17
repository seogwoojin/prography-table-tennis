package com.prography.tabletennis.domain.room.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.dto.request.UserInfoRequest;
import com.prography.tabletennis.domain.room.dto.response.RoomDetailInfoResponse;
import com.prography.tabletennis.domain.room.dto.response.RoomPageResponse;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.room.repository.UserRoomRepository;
import com.prography.tabletennis.domain.room.utils.RoomValidator;
import com.prography.tabletennis.domain.user.entity.User;
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
  private final UserRoomRepository userRoomRepository;

	@Transactional
	public void createNewRoom(CreateRoomRequest createRoomRequest) {
		User user = userService.getUserById(createRoomRequest.getUserId());
		roomValidator.validateUserIsEligibleForRoom(user);

		Room room = roomRepository.save(createRoomRequest.toEntity());
		UserRoom userRoom = UserRoom.builder().user(user).room(room).build();
		userRoomRepository.save(userRoom);
	}

	@Transactional
	public void joinRoom(Integer roomId, UserInfoRequest userInfoRequest) {
		User user = userService.getUserById(userInfoRequest.getUserId());
		Room room = getRoomById(roomId);
		roomValidator.validateUserCanJoinRoom(user, room);

		UserRoom userRoom = UserRoom.builder().user(user).room(room).build();
		userRoomRepository.save(userRoom);
	}

  @Transactional
  public void exitRoom(Integer userId, Integer roomId) {
    User user = userService.getUserById(userId);
    Room room = getRoomById(roomId);
    roomValidator.validateUserCanExitRoom(user, room);

    if (roomValidator.isUserRoomHost(user, room)) {
      closeRoom(room);
    } else {
      userRoomRepository.delete(user.getUserRoom());
    }
    user.exitRoom();
  }

  private void closeRoom(Room room) {
    room.updateRoomStatus(RoomStatus.FINISH);
    userRoomRepository.deleteAll(room.getUserRoomList());
  }

  public RoomPageResponse getRoomInfos(PageRequest pageRequest) {
    Page<Room> rooms = roomRepository.findAll(pageRequest);
    return RoomPageResponse.from(rooms);
  }

	public RoomDetailInfoResponse getRoomDetailInfo(Integer roomId) {
		Room room = getRoomById(roomId);
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
