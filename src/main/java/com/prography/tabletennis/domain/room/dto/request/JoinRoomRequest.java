package com.prography.tabletennis.domain.room.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinRoomRequest {
    private Integer userId;
}
