package com.prography.tabletennis.domain.init.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.init.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.RoomType;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.UserRoom;
import com.prography.tabletennis.domain.user.repository.UserRepository;
import com.prography.tabletennis.domain.user.repository.UserRoomRepository;
import com.prography.tabletennis.domain.user.service.UserRoomService;
import com.prography.tabletennis.domain.user.service.UserService;

@SpringBootTest
class InitServiceTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoomRepository userRoomRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	InitService initService;

	@Test
	@Transactional
	public void initTest() {
		//given
		User testUser = User.builder().build();
		Room testRoom = Room.builder().roomType(RoomType.SINGLE).build();
		UserRoom testUserRoom = UserRoom.builder().user(testUser).room(testRoom).build();
		userRepository.save(testUser);
		roomRepository.save(testRoom);
		userRoomRepository.save(testUserRoom);

		//when
		initService.initializeDatabase(new InitDataRequest(1, 5));

		//then
		List<User> userList = userRepository.findAll();
		assertThat(userList.size()).isEqualTo(5);
	}

}