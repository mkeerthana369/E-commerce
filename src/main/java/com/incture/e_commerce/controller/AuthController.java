package com.incture.e_commerce.controller;

import com.incture.e_commerce.dto.AuthRequest;
import com.incture.e_commerce.dto.AuthResponse;
import com.incture.e_commerce.entity.User;
import com.incture.e_commerce.service.UserService;
import com.incture.e_commerce.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User saved = userService.register(user);
        String token = jwtUtil.generateToken(saved.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        User u = userService.login(req.getEmail(), req.getPassword());
        String token = jwtUtil.generateToken(u.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
