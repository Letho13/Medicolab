package com.medicolab.gateway.controller;

import com.medicolab.gateway.config.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class AuthController {

    private final JwtUtil jwtUtil;
    private final ReactiveAuthenticationManager authenticationManager;

    public AuthController(JwtUtil jwtUtil, ReactiveAuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .map(auth -> {
                    String token = jwtUtil.generateToken(username);
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"))));
    }
}
