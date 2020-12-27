package com.youssef.testo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.youssef.testo.entity.User;
import com.youssef.testo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User getUserByName(String userName) {
		Optional<User> optUser = userRepository.findById(userName);

		if (optUser.isPresent()) {
			return optUser.get();
		} else {
			return null;
		}
	}
}
