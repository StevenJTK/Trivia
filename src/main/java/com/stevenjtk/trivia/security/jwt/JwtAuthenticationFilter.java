package com.stevenjtk.trivia.security.jwt;

import com.stevenjtk.trivia.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("---- JwtAuthentication Start ----");

        try {
            String token = extractJwtFromCookie(request);
            if(token != null) token = extractJwtFromRequest(request);
            if(token != null && jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                logger.debug("Token: {}, valid: {}", token, jwtUtil.validateToken(token));

                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);

                    if(userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.debug("User '{}' has been successfully authenticated", username);
                    }
                }
            }
        }           catch (Exception e) {
                    logger.error("User '{}' could not be authenticated", e.getMessage());
        }
                    filterChain.doFilter(request, response);
                    logger.debug("---- JwtAuthentication End -----");
    }


    private String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public String extractJwtFromCookie(HttpServletRequest request) {
        if(request.getCookies() == null)
            return null;

        for(Cookie cookie: request.getCookies()) {
            if("authToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
