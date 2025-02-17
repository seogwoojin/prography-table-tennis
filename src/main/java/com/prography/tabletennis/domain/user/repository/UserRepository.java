package com.prography.tabletennis.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.tabletennis.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
