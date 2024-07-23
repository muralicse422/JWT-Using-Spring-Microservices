package com.sample.example.JWTDemoUsingApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sample.example.JWTDemoUsingApi.model.User;
import com.sample.example.JWTDemoUsingApi.repository.UserRepository;

@Service
public class UserService {
 
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUserName(username);
	}
}
