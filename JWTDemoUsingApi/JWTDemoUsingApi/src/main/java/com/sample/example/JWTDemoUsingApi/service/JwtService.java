package com.sample.example.JWTDemoUsingApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.sample.example.JWTDemoUsingApi.model.AuthenticationRequest;
import com.sample.example.JWTDemoUsingApi.util.JwtUtil;

public class JwtService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public String createJwtToken(AuthenticationRequest authenticationRequest) throws Exception {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
				authenticationRequest.getPassword()));
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		return jwtUtil.generateToken(userDetails);
		
	}

}
