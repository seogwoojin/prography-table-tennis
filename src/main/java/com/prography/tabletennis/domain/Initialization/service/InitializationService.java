package com.prography.tabletennis.domain.Initialization.service;

import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.Initialization.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.Initialization.dto.response.FakerApiResponse;
import com.prography.tabletennis.domain.room.repository.UserRoomRepository;
import com.prography.tabletennis.domain.room.service.RoomService;
import com.prography.tabletennis.domain.user.service.UserService;
import com.prography.tabletennis.global.external.FakerApiService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InitializationService {
  private final UserService userService;
  private final RoomService roomService;
  private final FakerApiService fakerApiService;
  private final UserRoomRepository userRoomRepository;
  private final EntityManager entityManager;

  @Transactional
  public void initializeDatabase(InitDataRequest initDataRequest) {
    clearDatabase();

    FakerApiResponse fakerApiResponse =
        fakerApiService.getFakeUsers(initDataRequest.getSeed(), initDataRequest.getQuantity());
    userService.saveFakeUsers(fakerApiResponse.getUserDataList());
  }

  private void clearDatabase() {
    userRoomRepository.deleteAll();
    userService.deleteAll();
    roomService.deleteAll();
    entityManager.flush();
  }
}
