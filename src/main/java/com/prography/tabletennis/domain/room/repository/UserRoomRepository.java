package com.prography.tabletennis.domain.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.tabletennis.domain.room.entity.UserRoom;

public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {}
