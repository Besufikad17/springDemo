package com.example.springdemo.config;

import com.example.springdemo.helper.Exceptions;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component @Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt, userEmail;

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUserName(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    if(request.getRequestURI().equals("/users") || request.getRequestURI().startsWith("/user") || request.getRequestURI().startsWith("/remove")) {
                        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new Exceptions.UserNotFoundException("User not found!!"));
                        log.info("Checking if {} role is {}", user.getEmail(), user.getRole().toString());
                        if(user.getRole().toString().equals("ADMIN")) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            authToken.setDetails(
                                    new WebAuthenticationDetailsSource().buildDetails(request)
                            );
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }else {
                           throw new Exceptions.UnAuthorizedException("Unauthorized!!");
                        }
                    }else {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }else {
                    throw new Exceptions.UnAuthorizedException("Unauthorized!!");
                }
                filterChain.doFilter(request, response);
            }
        }catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            try {
                String msg = String.format("%s", e.getMessage());
                response.getOutputStream().print("{ \"error\": \"" + msg + "\" }");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
}
