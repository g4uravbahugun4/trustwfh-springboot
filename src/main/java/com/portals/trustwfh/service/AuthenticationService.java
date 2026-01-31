package com.portals.trustwfh.service;

import com.portals.trustwfh.dto.LoginResponse;
import com.portals.trustwfh.dto.LoginRequest;
import com.portals.trustwfh.dto.RegisterRequest;
import com.portals.trustwfh.entity.UserEntity;

import com.portals.trustwfh.model.Role;
import com.portals.trustwfh.repository.UserRepository;
import com.portals.trustwfh.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final DeviceRegistrationService deviceRegistrationService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository,
            DeviceRegistrationService deviceRegistrationService,
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.deviceRegistrationService = deviceRegistrationService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        // 1. Create User
        UserEntity user = new UserEntity(
                request.getUsername(), // Using username as userId for simplicity, or generate UUID
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);

        // 2. Register Device
        // We use the same userId (username for now) to bind the device
        deviceRegistrationService.registerDevice(
                request.getUsername(),
                request.getDeviceSignature(),
                request.getIp(),
                request.getDeviceToken(),
                request.getFingerprint(),
                request.getCertThumbprint());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        // 1. Authenticate username/password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        // 2. Generate Token
        // Login is now pure Authentication. No risk evaluation here.
        String token = tokenProvider.generateToken(authentication);

        // 3. Return Token Only
        return new LoginResponse(token);
    }
}
