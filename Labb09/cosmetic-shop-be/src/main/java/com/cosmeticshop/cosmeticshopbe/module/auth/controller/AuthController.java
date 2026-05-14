package com.cosmeticshop.cosmeticshopbe.module.auth.controller;

import com.cosmeticshop.cosmeticshopbe.module.auth.dto.AuthResponseDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.dto.LoginRequestDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.dto.RegisterRequestDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.dto.TokenRefreshRequestDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.service.AuthService;
import com.cosmeticshop.cosmeticshopbe.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successfully"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request) {
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Register successfully"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refreshToken(@Valid @RequestBody TokenRefreshRequestDTO request) {
        AuthResponseDTO response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(response, "Refresh token successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody TokenRefreshRequestDTO request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(null, "Logout successfully"));
    }
}
