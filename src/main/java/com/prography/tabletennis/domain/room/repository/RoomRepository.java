package com.prography.tabletennis.domain.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.tabletennis.domain.room.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
