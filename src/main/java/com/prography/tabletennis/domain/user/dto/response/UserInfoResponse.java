package com.prography.tabletennis.domain.user.dto.response;

import static com.prography.tabletennis.global.utils.GlobalConstant.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"id", "fakerId", "name", "email", "status", "createdAt", "updatedAt"})
public class UserInfoResponse {
  Integer id;
  Integer fakerId;
  String name;
  String email;

  @JsonProperty("status")
  UserStatus userStatus;

  String createdAt;
  String updatedAt;

  @Builder
  public UserInfoResponse(
      Integer id,
      Integer fakerId,
      String name,
      String email,
      UserStatus userStatus,
      String createdAt,
      String updatedAt) {
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
        .createdAt(user.getCreatedAt().format(DATE_TIME_FORMATTER))
        .updatedAt(user.getUpdatedAt().format(DATE_TIME_FORMATTER))
        .build();
  }
}
