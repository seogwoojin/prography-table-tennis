package com.prography.tabletennis.domain.room.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import com.prography.tabletennis.domain.Initialization.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.Initialization.service.InitializationService;
import com.prography.tabletennis.domain.room.controller.RoomController;
import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.repository.UserRepository;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

@SpringBootTest
public class ExitRoomIntegrationTest {
  @Autowired RoomService roomService;
  @Autowired RoomController roomController;
  @Autowired InitializationService initializationService;
  @Autowired UserRepository userRepository;
  @Autowired RoomRepository roomRepository;
  @Autowired TransactionTemplate transactionTemplate;
  @Autowired EntityManager entityManager;

  @BeforeEach
  public void initData() {
    initializationService.initializeDatabase(new InitDataRequest(1, 6));
  }

  @Test
  @DisplayName("호스트가 방을 나가면 방의 상태가 종료로 변하고, 내부 참가자들은 모두 방을 나간다.")
  void host_exitRoom_success() {
    // given
    List<User> users = userRepository.findAll();
    User host = users.get(0);
    User guest = users.get(1);
    roomService.createNewRoom(new CreateRoomRequest(host.getId(), RoomType.SINGLE, "TEST"));
    Room room = roomRepository.findAll().get(0);
    roomService.joinRoom(guest.getId(), room.getId());

    // when
    roomService.exitRoom(host.getId(), room.getId());

    // then
    transactionTemplate.executeWithoutResult(
        status -> {
          Room afterRoom = roomRepository.findById(room.getId()).get();
          User afterGuest = userRepository.findById(guest.getId()).get();

          assertThat(afterRoom.getRoomStatus()).isEqualTo(RoomStatus.FINISH);
          assertThat(afterRoom.getUserRoomList().size()).isEqualTo(0);
          assertNull(afterGuest.getUserRoom());
        });
  }

  @Test
  @DisplayName("방에 참여하지 않은 유저가 방 나가기를 요청하면 예외가 발생한다.")
  void not_Join_user_exitRoom_fail() {
    // given
    List<User> users = userRepository.findAll();
    User host = users.get(0);
    User notJoin_user = users.get(1);
    roomService.createNewRoom(new CreateRoomRequest(host.getId(), RoomType.SINGLE, "TEST"));
    Room room = roomRepository.findAll().get(0);

    assertThatThrownBy(() -> roomService.exitRoom(notJoin_user.getId(), room.getId()))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining(ReturnCode.WRONG_REQUEST.getMessage());
  }
}
