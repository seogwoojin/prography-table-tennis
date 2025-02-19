package com.prography.tabletennis.domain.init.utils;

import com.prography.tabletennis.domain.user.entity.enums.UserStatus;

public class UserStatusUtil {
  private static final Integer MAX_ACTIVE_NUMBER = 30;
  private static final Integer MAX_WAIT_NUMBER = 60;

  public static UserStatus getUserStatusFromId(int id) {
    if (id <= MAX_ACTIVE_NUMBER) {
      return UserStatus.ACTIVE;
    } else if (id <= MAX_WAIT_NUMBER) {
      return UserStatus.WAIT;
    } else {
      return UserStatus.NON_ACTIVE;
    }
  }
}
