package com.prography.tabletennis.domain.room.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.enums.RoomType;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.room.utils.RoomValidator;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.service.UserService;
import com.prography.tabletennis.global.response.CustomException;
import com.prography.tabletennis.global.response.ReturnCode;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
  @Mock private RoomRepository roomRepository;

  @Mock private RoomValidator roomValidator;

  @Mock private UserService userService;
  @InjectMocks RoomService roomService;

  @Test
  @DisplayName("유저는 validateUserCanCreateRoom을 검증하지 못하면 방을 생성할 수 없다.")
  public void createRoomServiceTest() {
    // Given
    User mockUser = mock(User.class);
    CreateRoomRequest createRoomRequest = new CreateRoomRequest(1, RoomType.SINGLE, "새로운 방");
    when(userService.getUserById(1)).thenReturn(mockUser);
    doThrow(new CustomException(ReturnCode.WRONG_REQUEST))
        .when(roomValidator)
        .validateUserCanCreateRoom(mockUser);
    // When, Then
    assertThatThrownBy(() -> roomService.createNewRoom(createRoomRequest))
        .isInstanceOf(CustomException.class);
  }

  @Test
  @DisplayName("유저는 validateUserCanExitRoom 검증하지 못하면 방을 나갈 수 없다.")
  public void createRoomServiceWithStatus() {
    // Given
    User mockUser = mock(User.class);
    Room mockRoom = mock(Room.class);
    when(userService.getUserById(1)).thenReturn(mockUser);
    when(roomRepository.findById(1)).thenReturn(Optional.ofNullable(mockRoom));
    doThrow(new CustomException(ReturnCode.WRONG_REQUEST))
        .when(roomValidator)
        .validateUserCanExitRoom(mockUser, mockRoom);
    // When, Then
    assertThatThrownBy(() -> roomService.exitRoom(1, 1)).isInstanceOf(CustomException.class);
  }
}
