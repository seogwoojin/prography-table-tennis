package com.prography.tabletennis.domain.room.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prography.tabletennis.domain.room.dto.request.CreateRoomRequest;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.RoomType;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;
import com.prography.tabletennis.domain.user.service.UserService;
import com.prography.tabletennis.global.response.CustomException;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    @Mock private RoomRepository roomRepository;

    @Mock private UserService userService;
    @InjectMocks RoomService roomService;

    @Test
    @DisplayName("유저는 방을 두개 이상 생성할 수 없다.")
    public void createRoomServiceTest() {
        // Given
        User mockUser = User.builder().userStatus(UserStatus.ACTIVE).build();
        mockUser.addUserRoom(Room.builder().title("기존 방").build());
        CreateRoomRequest createRoomRequest = new CreateRoomRequest(1, RoomType.SINGLE, "새로운 방");
        when(userService.getUserById(1)).thenReturn(mockUser);

        // When, Then
        assertThatThrownBy(() -> roomService.createNewRoom(createRoomRequest))
                .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("ACTIVE 상태가 아닌 유저는 방을 생성할 수 없다.")
    public void createRoomServiceWithStatus() {
        // Given
        User mockUser = User.builder().userStatus(UserStatus.WAIT).build();
        CreateRoomRequest createRoomRequest = new CreateRoomRequest(1, RoomType.SINGLE, "새로운 방");
        when(userService.getUserById(1)).thenReturn(mockUser);

        // When, Then
        assertThatThrownBy(() -> roomService.createNewRoom(createRoomRequest))
                .isInstanceOf(CustomException.class);
    }
}
