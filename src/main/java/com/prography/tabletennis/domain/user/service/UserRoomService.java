package com.prography.tabletennis.domain.user.service;

import org.springframework.stereotype.Service;

import com.prography.tabletennis.domain.user.repository.UserRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRoomService {
    private final UserRoomRepository userRoomRepository;

    public void deleteAll() {
        userRoomRepository.deleteAll();
    }
}
