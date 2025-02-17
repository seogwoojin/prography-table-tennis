package com.prography.tabletennis.domain.room.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.dto.request.JoinRoomRequest;
import com.prography.tabletennis.domain.room.dto.response.RoomDetailInfoResponse;
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
		validateUserIsEligibleForRoom(user);
		Room room = roomRepository.save(createRoomRequest.toEntity());
		user.addUserRoom(room);
	}

	@Transactional
	public void joinRoom(Integer roomId, JoinRoomRequest joinRoomRequest) {
		User user = userService.getUserById(joinRoomRequest.getUserId());
		Room room =
			roomRepository
				.findById(roomId)
				.orElseThrow(() -> new CustomException(ReturnCode.WRONG_REQUEST));
		validateUserCanJoinRoom(user, room);
		user.addUserRoom(room);
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

	@Transactional
	public void deleteAll() {
		roomRepository.deleteAll();
	}

	/** 사용자가 방 참여 가능한지(활성화 상태 + 다른 방에 속해있지 않은지) 검증 */
	private void validateUserIsEligibleForRoom(User user) {
		if (user.getUserStatus() != UserStatus.ACTIVE || user.getUserRoom() != null) {
			throw new CustomException(ReturnCode.WRONG_REQUEST);
		}
	}

	/** 방 참여 시 추가로 필요한 검증 로직 */
	private void validateUserCanJoinRoom(User user, Room room) {
		validateUserIsEligibleForRoom(user);
		validateRoomIsNotFull(room);
	}

	/** 방이 정원(예: 2명)을 초과했는지 검사 */
	private void validateRoomIsNotFull(Room room) {
		if (room.isFull()) {
			throw new CustomException(ReturnCode.WRONG_REQUEST);
		}
	}
}
