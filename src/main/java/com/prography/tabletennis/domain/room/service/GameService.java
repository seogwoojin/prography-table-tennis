package com.prography.tabletennis.domain.room.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.room.entity.Room;
import com.prography.tabletennis.domain.room.entity.enums.RoomStatus;
import com.prography.tabletennis.domain.room.repository.RoomRepository;
import com.prography.tabletennis.domain.room.repository.UserRoomRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GameService {
  private final RoomRepository roomRepository;
  private final UserRoomRepository userRoomRepository;

  /** 새로운 트랜잭션 내에서 게임 종료 처리를 수행합니다. */
  @Transactional
  public void finishGame(Room room) {
    log.info("{}번 방 게임 종료 - 종료 시간 : {} ", room.getId(), LocalDateTime.now());
    room.updateRoomStatus(RoomStatus.FINISH);
    userRoomRepository.deleteAll(room.getUserRoomList());
    roomRepository.save(room);
  }
}
