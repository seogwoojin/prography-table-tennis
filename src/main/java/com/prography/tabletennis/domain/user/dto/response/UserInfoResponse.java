package com.prography.tabletennis.domain.user.dto.response;

import java.util.Date;

import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponse {
	Integer id;
	Integer fakerId;
	String name;
	String email;
	UserStatus userStatus;
	Date createdAt;
	Date updatedAt;

	@Builder
	public UserInfoResponse(
		Integer id,
		Integer fakerId,
		String name,
		String email,
		UserStatus userStatus,
		Date createdAt,
		Date updatedAt
	) {
		this.id = id;
		this.fakerId = fakerId;
		this.name = name;
		this.email = email;
		this.userStatus = userStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static UserInfoResponse from(User user) {
		return UserInfoResponse.builder()
			.id(user.getId())
			.fakerId(user.getFakerId())
			.name(user.getName())
			.email(user.getEmail())
			.userStatus(user.getUserStatus())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
