package com.prography.tabletennis.domain.init.utils;

import com.prography.tabletennis.domain.user.entity.enums.UserStatus;

public class UserStatusUtil {
	public static UserStatus getUserStatusFromId(int id) {
		if (id <= 30) {
			return UserStatus.ACTIVE;
		} else if (id <= 60) {
			return UserStatus.WAIT;
		} else {
			return UserStatus.NON_ACTIVE;
		}
	}
}
