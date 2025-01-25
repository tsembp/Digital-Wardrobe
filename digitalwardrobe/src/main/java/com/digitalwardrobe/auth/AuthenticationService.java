package com.digitalwardrobe.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.digitalwardrobe.config.JwtService;
import com.digitalwardrobe.models.*;
import com.digitalwardrobe.repository.AdminRepository;
import com.digitalwardrobe.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepository userRepository;
        private final AdminRepository adminRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse registerUser(RegisterRequest request) {
                User user = User.builder()
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build();
                userRepository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                // Authenticate credentials using the authentication manager
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

                // Try to find the user in the UserRepository
                var user = userRepository.findByUsername(request.getUsername()).orElse(null);
                if (user != null) {
                        // Generate JWT token for the user
                        var jwtToken = jwtService.generateToken(user);
                        return AuthenticationResponse.builder()
                                        .token(jwtToken)
                                        .build();
                }

                // If not found in UserRepository, fallback to AdminRepository
                var admin = adminRepository.findByUsername(request.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("User or Admin not found."));

                // Generate JWT token for the admin
                var jwtToken = jwtService.generateToken(admin);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        public AuthenticationResponse registerAdmin(RegisterRequest request) {
                Admin user = (Admin)User.builder()
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.ADMIN)
                                .build();
                adminRepository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }
        
}       
