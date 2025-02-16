package com.prography.tabletennis.domain.room.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.dto.response.RoomPageResponse;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
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

	@Transactional
	public void createNewRoom(CreateRoomRequest createRoomRequest) {
		User user = userService.getUserById(createRoomRequest.getUserId());
		if (!checkUserCanCreateRoom(user)) {
			throw new CustomException(ReturnCode.WRONG_REQUEST);
		}
		Room room = roomRepository.save(createRoomRequest.toEntity());
		user.addUserRoom(room);
	}

	public RoomPageResponse getRoomInfos(PageRequest pageRequest) {
		Page<Room> rooms = roomRepository.findAll(pageRequest);
		return RoomPageResponse.from(rooms);
	}

	private boolean checkUserCanCreateRoom(User user) {
		if (user.getUserStatus() != UserStatus.ACTIVE) {
			return false;
		}
		if (user.getUserRoom() != null) {
			return false;
		}
		return true;
	}

	public void deleteAll() {
		roomRepository.deleteAll();
	}

}
