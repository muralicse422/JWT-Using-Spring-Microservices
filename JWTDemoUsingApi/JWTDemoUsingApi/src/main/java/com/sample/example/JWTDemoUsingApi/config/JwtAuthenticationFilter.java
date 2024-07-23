package com.sample.example.JWTDemoUsingApi.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sample.example.JWTDemoUsingApi.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil =jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = null;
		String jwt = null;
		if((authorizationHeader != null) &&
		authorizationHeader.startsWith("Bearer")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUserName(jwt);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() ==null) {
			UserDetails userDetails =userDetailsService.loadUserByUsername(username);
			if(jwtUtil.validToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authentication =new 
						UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
			
		}
	}

}
