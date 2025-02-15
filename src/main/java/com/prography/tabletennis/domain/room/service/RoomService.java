package com.prography.tabletennis.domain.room.service;

import org.springframework.stereotype.Service;

import com.prography.tabletennis.domain.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomService {
	private final RoomRepository roomRepository;

	public void deleteAll() {
		roomRepository.deleteAll();
	}

}
