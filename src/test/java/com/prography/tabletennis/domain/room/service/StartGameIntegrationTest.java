package com.prography.tabletennis.domain.room.service;

import static com.prography.tabletennis.domain.room.utils.GameConstants.GAME_PROGRESS_TIME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prography.tabletennis.domain.Initialization.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.Initialization.service.InitializationService;
import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.room.utils.RoomValidator;
import com.prography.tabletennis.domain.room.utils.TeamAssignmentService;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.repository.UserRepository;

/** Start Game -> Single Double Test 클래스입니다. */
@SpringBootTest
public class StartGameIntegrationTest {
  @Autowired private RoomRepository roomRepository;
  @Autowired private InitializationService initializationService;
  @Autowired private RoomValidator roomValidator;

  @Autowired private UserRepository userRepository;
  @Autowired private TeamAssignmentService teamAssignmentService;

  @Autowired private RoomService roomService;

  @BeforeEach
  public void initData() {
    initializationService.initializeDatabase(new InitDataRequest(1, 6));
  }

  @Test
  public void testStartGame() {
    // given
    List<User> users = userRepository.findAll();
    User hostUser = users.get(0);
    User joinUser = users.get(1);
    roomService.createNewRoom(new CreateRoomRequest(hostUser.getId(), RoomType.SINGLE, "TEST"));
    List<Room> rooms = roomRepository.findAll();
    Integer testRoomId = rooms.get(0).getId();
    roomService.joinRoom(joinUser.getId(), testRoomId);

    // when
    roomService.startGame(hostUser.getId(), 1);

    // Then
    // Awaitility를 사용, 50초 이후 1초에 한번 씩 풀링을 통해, room 상태가 FINISH로 변경되는지 확인합니다.
    await()
        .atMost(Duration.ofSeconds(GAME_PROGRESS_TIME + 5))
        .pollInterval(1000, TimeUnit.MILLISECONDS)
        .until(
            () -> roomService.getRoomDetailInfo(testRoomId).getRoomStatus() == RoomStatus.FINISH);

    assertEquals(RoomStatus.FINISH, roomService.getRoomDetailInfo(testRoomId).getRoomStatus());
  }

  @Test
  public void testStartGameDouble() {
    // given
    List<User> users = userRepository.findAll();
    User hostUser = users.get(0);
    User user2 = users.get(1);
    User user3 = users.get(2);
    User user4 = users.get(3);

    roomService.createNewRoom(new CreateRoomRequest(hostUser.getId(), RoomType.DOUBLE, "TEST"));
    List<Room> rooms = roomRepository.findAll();
    Room testRoom = rooms.get(0);

    roomService.joinRoom(user2.getId(), testRoom.getId());
    roomService.joinRoom(user3.getId(), testRoom.getId());
    roomService.joinRoom(user4.getId(), testRoom.getId());

    // when
    roomService.startGame(hostUser.getId(), testRoom.getId());

    // then
    assertThat(roomRepository.findById(testRoom.getId()).get().getRoomStatus())
        .isEqualTo(RoomStatus.PROGRESS);

    await()
        .pollDelay(Duration.ofSeconds(50))
        .atMost(Duration.ofSeconds(GAME_PROGRESS_TIME + 5))
        .until(
            () ->
                roomService.getRoomDetailInfo(testRoom.getId()).getRoomStatus()
                    == RoomStatus.FINISH);

    assertEquals(
        RoomStatus.FINISH, roomService.getRoomDetailInfo(testRoom.getId()).getRoomStatus());
  }
}
