package com.prography.tabletennis.domain.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prography.tabletennis.domain.Initialization.dto.response.FakerApiResponse;
import com.prography.tabletennis.global.external.FakerApiService;

@SpringBootTest
class FakerApiServiceTest {
  @Autowired FakerApiService fakerApiService;

  @Test
  @DisplayName("Quantity 값에 따라 User 수가 결정된다.")
  public void APITest() {
    FakerApiResponse fakeUsers = fakerApiService.getFakeUsers(1, 1);

    assertThat(Integer.parseInt(fakeUsers.getSeed())).isEqualTo(1);
    assertThat(fakeUsers.getUserDataList().size()).isEqualTo(1);
  }
}
