package com.prography.tabletennis.domain.user.dto.response;

import org.springframework.data.domain.Page;

import com.prography.tabletennis.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResultResponse {
	UserPageResponse result;

	@Builder
	public UserResultResponse(UserPageResponse result) {
		this.result = result;
	}

	public static UserResultResponse from(Page<User> users) {
		UserPageResponse userPageResponse = UserPageResponse.from(users);
		return UserResultResponse.builder().result(userPageResponse).build();
	}
}
