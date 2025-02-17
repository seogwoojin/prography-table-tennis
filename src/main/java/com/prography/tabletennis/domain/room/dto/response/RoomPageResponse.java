package com.prography.tabletennis.domain.room.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.prography.tabletennis.domain.room.entity.Room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomPageResponse {
  Integer totalElements;
  Integer totalPages;
  List<RoomInfoResponse> userList;

  @Builder
  public RoomPageResponse(
      Integer totalElements, Integer totalPages, List<RoomInfoResponse> userList) {
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.userList = userList;
  }

  public static RoomPageResponse from(Page<Room> rooms) {
    List<RoomInfoResponse> userList =
        rooms.getContent().stream().map(RoomInfoResponse::from).toList();
    return RoomPageResponse.builder()
        .totalPages(rooms.getTotalPages())
        .totalElements(Math.toIntExact(rooms.getTotalElements()))
        .userList(userList)
        .build();
  }
}
