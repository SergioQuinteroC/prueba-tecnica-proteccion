package com.proteccion.prueba.demo.controllers;

import com.proteccion.prueba.demo.dtos.AuthRequest;
import com.proteccion.prueba.demo.dtos.AuthResponse;
import com.proteccion.prueba.demo.dtos.RegisterRequest;
import com.proteccion.prueba.demo.entities.User;
import com.proteccion.prueba.demo.security.JwtService;
import com.proteccion.prueba.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        
        User savedUser = authService.registerUser(user);
        
        UserDetails userDetails = authService.loadUserByUsername(savedUser.getEmail());
        String token = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .message("Usuario registrado exitosamente")
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserDetails userDetails = authService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .message("Login exitoso")
                .build());
    }
} 