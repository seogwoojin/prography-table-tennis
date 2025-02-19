package com.prography.tabletennis.domain.user.utils;

import com.prography.tabletennis.domain.user.entity.enums.UserStatus;

/**
 * 주어진 id에 따라 사용자 상태를 반환합니다.
 *
 * @param id 사용자 식별 번호
 * @return id 값에 따른 {@link UserStatus}
 */
public final class UserStatusUtil {
  private static final int MAX_ACTIVE_NUMBER = 30;
  private static final int MAX_WAIT_NUMBER = 60;

  public static UserStatus getUserStatusFromId(int id) {
    if (id <= MAX_ACTIVE_NUMBER) {
      return UserStatus.ACTIVE;
    } else if (id <= MAX_WAIT_NUMBER) {
      return UserStatus.WAIT;
    } else {
      return UserStatus.NON_ACTIVE;
    }
  }

  private UserStatusUtil() {}
}
