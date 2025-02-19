package com.prography.tabletennis.domain.Initialization.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prography.tabletennis.domain.Initialization.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.UserRoom;
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
  public void initTest() {
    // given
    User testUser = User.builder().build();
    Room testRoom = Room.builder().roomType(RoomType.SINGLE).build();
    UserRoom testUserRoom = UserRoom.builder().user(testUser).room(testRoom).build();
    userRepository.save(testUser);
    roomRepository.save(testRoom);
    userRoomRepository.save(testUserRoom);

    // when
    initializationService.initializeDatabase(new InitDataRequest(1, 5));

    // then
    List<User> userList = userRepository.findAll();
    assertThat(userList.size()).isEqualTo(5);
  }
}
