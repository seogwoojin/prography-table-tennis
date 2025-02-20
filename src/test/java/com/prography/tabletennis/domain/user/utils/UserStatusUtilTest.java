package com.prography.tabletennis.domain.user.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prography.tabletennis.domain.user.entity.enums.UserStatus;

@DisplayName("UserStatusUtil 테스트 - 경계값 위주")
class UserStatusUtilTest {

  @Test
  @DisplayName("id가 MAX_ACTIVE_NUMBER 이하인 경우 ACTIVE 상태를 반환해야 함 (예: -1, 0, 1, 30)")
  void testGetUserStatusFromId_active() {
    assertThat(UserStatusUtil.getUserStatusFromId(-1)).isEqualTo(UserStatus.ACTIVE);
    assertThat(UserStatusUtil.getUserStatusFromId(0)).isEqualTo(UserStatus.ACTIVE);
    assertThat(UserStatusUtil.getUserStatusFromId(1)).isEqualTo(UserStatus.ACTIVE);
    assertThat(UserStatusUtil.getUserStatusFromId(30)).isEqualTo(UserStatus.ACTIVE);
  }

  @Test
  @DisplayName("id가 MAX_ACTIVE_NUMBER 초과, MAX_WAIT_NUMBER 이하인 경우 WAIT 상태를 반환해야 함 (예: 31, 60)")
  void testGetUserStatusFromId_wait() {
    assertThat(UserStatusUtil.getUserStatusFromId(31)).isEqualTo(UserStatus.WAIT);
    assertThat(UserStatusUtil.getUserStatusFromId(60)).isEqualTo(UserStatus.WAIT);
  }

  @Test
  @DisplayName("id가 MAX_WAIT_NUMBER 초과인 경우 NON_ACTIVE 상태를 반환해야 함 (예: 61, 100)")
  void testGetUserStatusFromId_nonActive() {
    assertThat(UserStatusUtil.getUserStatusFromId(61)).isEqualTo(UserStatus.NON_ACTIVE);
    assertThat(UserStatusUtil.getUserStatusFromId(100)).isEqualTo(UserStatus.NON_ACTIVE);
  }
}
