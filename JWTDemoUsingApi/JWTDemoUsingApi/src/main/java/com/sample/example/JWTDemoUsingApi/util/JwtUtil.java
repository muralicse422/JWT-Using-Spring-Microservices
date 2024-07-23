package com.sample.example.JWTDemoUsingApi.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims= extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	public Boolean validToken(String token, UserDetails userDetails) {
		final String  username = extractUserName(token);
		return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToke(claims, userDetails.getUsername());
	}

	private String createToke(Map<String, Object> claims, String subject) {
		return 
				Jwts.builder().setClaims(claims).setSubject(subject).
				setIssuedAt(new Date(System.currentTimeMillis())).
				setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10)).
				signWith(SignatureAlgorithm.HS256,secret).compact();
	}

}
