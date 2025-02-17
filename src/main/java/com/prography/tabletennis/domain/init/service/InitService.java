package com.prography.tabletennis.domain.init.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.init.dto.request.InitDataRequest;
import com.prography.tabletennis.domain.init.dto.response.FakerApiResponse;
import com.prography.tabletennis.domain.room.service.RoomService;
import com.prography.tabletennis.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InitService {
  private final UserService userService;
  private final RoomService roomService;
  private final FakerApiService fakerApiService;

  @Transactional
  public void initializeDatabase(InitDataRequest initDataRequest) {
    deleteAllColumn();

    FakerApiResponse fakerApiResponse =
        fakerApiService.getFakeUsers(initDataRequest.getSeed(), initDataRequest.getQuantity());
    userService.saveFakeUsers(fakerApiResponse.getUserDataList());
  }

  private void deleteAllColumn() {
    userService.deleteAll();
    roomService.deleteAll();
  }
}
