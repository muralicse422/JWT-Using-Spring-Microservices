package com.sample.example.JWTDemoUsingApi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sample.example.JWTDemoUsingApi.model.User;
import com.sample.example.JWTDemoUsingApi.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username: "+username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
		
	}

}
