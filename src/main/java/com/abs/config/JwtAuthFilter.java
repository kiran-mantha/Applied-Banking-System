package com.abs.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abs.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		try {
			// Verify whether request has Authorization header and it has Bearer in it
			final String authHeader = request.getHeader("Authorization");
			final String jwt;
			final String email;

			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			// Extract jwt from the Authorization
			jwt = authHeader.substring(7);

			// Verify whether user is present in db and whether token is valid
			email = jwtService.extractUsername(jwt);

			// If user is present and no authentication object in securityContext
			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

				// If valid, set to security context holder
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		} catch (ExpiredJwtException ex) {
			handleJwtException(response, HttpServletResponse.SC_UNAUTHORIZED, "The provided JWT token has expired");
			return;
		} catch (MalformedJwtException ex) {
			handleJwtException(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token format");
			return;
		}catch (UsernameNotFoundException ex) {
			handleJwtException(response, HttpServletResponse.SC_UNAUTHORIZED, "User not found");
			return;
		} catch (Exception e) {
			handleJwtException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error occurred");
			return;
		}

		filterChain.doFilter(request, response);
		
	}

	private void handleJwtException(HttpServletResponse response, int statusCode, String message) throws IOException {
		LOGGER.info(message);
		response.setStatus(statusCode);
		response.getWriter().write(message);
	}

	// Verify if it is a whitelisted path and if yes, don't do anything
	@Override
	protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
		return request.getServletPath().contains("/auth");
	}
}
