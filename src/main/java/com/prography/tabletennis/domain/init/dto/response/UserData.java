package com.prography.tabletennis.domain.init.dto.response;

import com.prography.tabletennis.domain.init.utils.UserStatusUtil;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.entity.enums.UserStatus;

import lombok.Getter;

@Getter
public class UserData {
  private int id;
  private String uuid;
  private String firstname;
  private String lastname;
  private String username;
  private String password;
  private String email;
  private String ip;
  private String macAddress;
  private String website;
  private String image;

  public User toUser() {
    UserStatus userStatus = UserStatusUtil.getUserStatusFromId(id);
    return User.builder().fakerId(id).name(username).email(email).userStatus(userStatus).build();
  }
}
