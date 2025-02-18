package com.prography.tabletennis.domain.room.service;

import static com.prography.tabletennis.domain.room.utils.GameConstants.GAME_PROGRESS_TIME;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.dto.request.UserInfoRequest;
import com.prography.tabletennis.domain.room.dto.response.RoomDetailInfoResponse;
import com.prography.tabletennis.domain.room.dto.response.RoomPageResponse;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.TeamType;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.room.repository.UserRoomRepository;
import com.prography.tabletennis.domain.room.utils.RoomValidator;
import com.prography.tabletennis.domain.room.utils.TeamAssignmentService;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.service.UserService;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class RoomService {
  private final RoomRepository roomRepository;
  private final UserService userService;
  private final RoomValidator roomValidator;
  private final UserRoomRepository userRoomRepository;
  private final TeamAssignmentService teamAssignmentService;
  private final TaskScheduler taskScheduler;
  private final GameService gameService;

  @Transactional
  public void createNewRoom(CreateRoomRequest createRoomRequest) {
    User user = userService.getUserById(createRoomRequest.getUserId());
    roomValidator.validateUserCanCreateRoom(user);

    Room room = roomRepository.save(createRoomRequest.toEntity());
    createAndSaveUserRoom(user, room);
  }

  @Transactional
  public void joinRoom(Integer roomId, UserInfoRequest userInfoRequest) {
    User user = userService.getUserById(userInfoRequest.getUserId());
    Room room = getRoomById(roomId);
    roomValidator.validateUserCanJoinRoom(user, room);

    createAndSaveUserRoom(user, room);
  }

  @Transactional
  public void exitRoom(Integer userId, Integer roomId) {
    User user = userService.getUserById(userId);
    Room room = getRoomById(roomId);
    roomValidator.validateUserCanExitRoom(user, room);

    if (roomValidator.isUserRoomHost(user, room)) {
      gameService.finishGame(room);
    } else {
      userRoomRepository.delete(user.getUserRoom());
    }
    user.exitRoom();
  }

  /**
   * 게임을 시작하는 함수입니다. 조건을 만족할 때, 게임을 실행할 수 있습니다. 게임이 실행되면 Scheduler 쓰레드를 활용해 60초 뒤에 게임을 종료할 수 있습니다.
   *
   * @param userId
   * @param roomId
   */
  @Transactional
  public void startGame(Integer userId, Integer roomId) {
    User user = userService.getUserById(userId);
    Room room = getRoomById(roomId);
    roomValidator.validateStartGame(user, room);

    room.updateRoomStatus(RoomStatus.PROGRESS);
    taskScheduler.schedule(
        () -> gameService.finishGame(room),
        Instant.now().plus(GAME_PROGRESS_TIME, ChronoUnit.SECONDS));
  }

  public RoomPageResponse getRoomInfos(PageRequest pageRequest) {
    Page<Room> rooms = roomRepository.findAll(pageRequest);
    return RoomPageResponse.from(rooms);
  }

  public RoomDetailInfoResponse getRoomDetailInfo(Integer roomId) {
    Room room = getRoomById(roomId);
    return RoomDetailInfoResponse.from(room);
  }

  @Transactional
  public void deleteAll() {
    roomRepository.deleteAll();
  }

  private Room getRoomById(Integer roomId) {
    return roomRepository
        .findById(roomId)
        .orElseThrow(() -> new CustomException(ReturnCode.WRONG_REQUEST));
  }

  private void createAndSaveUserRoom(User user, Room room) {
    TeamType assignTeam = teamAssignmentService.assignTeam(room);
    UserRoom userRoom = UserRoom.builder().user(user).room(room).teamType(assignTeam).build();
    userRoomRepository.save(userRoom);
  }
}
