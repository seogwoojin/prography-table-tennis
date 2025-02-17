package com.prography.tabletennis.domain.user.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.prography.tabletennis.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPageResponse {
    Integer totalElements;
    Integer totalPages;
    List<UserInfoResponse> userList;

    @Builder
    public UserPageResponse(
            Integer totalElements, Integer totalPages, List<UserInfoResponse> userList) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.userList = userList;
    }

    public static UserPageResponse from(Page<User> users) {
        List<UserInfoResponse> userList =
                users.getContent().stream().map(UserInfoResponse::from).toList();
        return UserPageResponse.builder()
                .totalPages(users.getTotalPages())
                .totalElements(Math.toIntExact(users.getTotalElements()))
                .userList(userList)
                .build();
    }
}
