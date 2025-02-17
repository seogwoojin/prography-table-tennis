package com.prography.tabletennis.domain.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prography.tabletennis.domain.init.dto.response.FakerApiResponse;
import com.prography.tabletennis.domain.init.service.FakerApiService;

@SpringBootTest
class FakerApiServiceTest {
    @Autowired FakerApiService fakerApiService;

    @Test
    public void APITest() {
        FakerApiResponse fakeUsers = fakerApiService.getFakeUsers(1, 1);

        assertThat(Integer.parseInt(fakeUsers.getSeed())).isEqualTo(1);
        assertThat(fakeUsers.getUserDataList().size()).isEqualTo(1);
    }
}
