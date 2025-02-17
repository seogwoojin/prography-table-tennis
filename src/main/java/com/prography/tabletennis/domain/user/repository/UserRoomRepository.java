package com.prography.tabletennis.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.tabletennis.domain.user.entity.UserRoom;

public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {}
