package com.prography.tabletennis.domain.Initialization.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.Initialization.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.room.repository.UserRoomRepository;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.repository.UserRepository;

@SpringBootTest
class InitializationServiceTest {

  @Autowired UserRepository userRepository;

  @Autowired UserRoomRepository userRoomRepository;

  @Autowired RoomRepository roomRepository;

  @Autowired InitializationService initializationService;

  @Test
  @Transactional
  @DisplayName("초기화 요청 시, 기존 데이터는 모두 제거된다.")
  public void initTest() {
    // given
    User testUser = User.builder().fakerId(1).build();
    Room testRoom =
        Room.builder().roomType(RoomType.SINGLE).host(1).roomStatus(RoomStatus.WAIT).build();
    UserRoom testUserRoom = UserRoom.builder().user(testUser).room(testRoom).build();
    userRepository.save(testUser);
    roomRepository.save(testRoom);
    userRoomRepository.save(testUserRoom);

    // when
    initializationService.initializeDatabase(new InitDataRequest(1, 5));

    // then
    List<User> users = userRepository.findAll();
    List<Room> rooms = roomRepository.findAll();
    List<UserRoom> userRooms = userRoomRepository.findAll();
    assertThat(users.size()).isEqualTo(5);
    assertThat(rooms.size()).isEqualTo(0);
    assertThat(userRooms.size()).isEqualTo(0);
  }
}
