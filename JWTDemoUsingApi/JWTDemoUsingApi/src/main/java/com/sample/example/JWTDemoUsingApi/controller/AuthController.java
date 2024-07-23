package com.sample.example.JWTDemoUsingApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.example.JWTDemoUsingApi.model.AuthenticationRequest;
import com.sample.example.JWTDemoUsingApi.model.AuthenticationResponse;
import com.sample.example.JWTDemoUsingApi.model.User;
import com.sample.example.JWTDemoUsingApi.service.JwtService;
import com.sample.example.JWTDemoUsingApi.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthencationToken(@RequestBody AuthenticationRequest request)
	throws Exception{
		
		
		final String jwt =jwtService.createJwtToken(request);
		return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		if(userService.findByUsername(user.getUserName()) != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
