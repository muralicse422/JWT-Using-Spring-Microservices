package com.sample.example.JWTDemoUsingApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.example.JWTDemoUsingApi.model.User;

@Repository
public interface UserRepository	extends JpaRepository<User, Long> {
	User findByUserName(String userName);
}
