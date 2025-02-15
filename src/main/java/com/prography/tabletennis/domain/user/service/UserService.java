package com.prography.tabletennis.domain.user.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.tabletennis.domain.init.dto.response.UserData;
import com.prography.tabletennis.domain.user.dto.response.UserResultResponse;
import com.prography.tabletennis.domain.user.entity.User;
import com.prography.tabletennis.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;

	public UserResultResponse getUserInfos(PageRequest pageRequest) {
		Page<User> users = userRepository.findAll(pageRequest);
		return UserResultResponse.from(users);
	}

	@Transactional
	public void saveFakeUsers(List<UserData> fakeUsers) {
		List<User> sortedUserList = fakeUsers.stream()
			.sorted(Comparator.comparingInt(UserData::getId))
			.map(UserData::toUser)
			.toList();
		userRepository.saveAll(sortedUserList);
	}

	public void deleteAll() {
		userRepository.deleteAll();
	}
}
