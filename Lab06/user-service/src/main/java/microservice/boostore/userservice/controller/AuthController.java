package microservice.boostore.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import microservice.bookstore.common.dto.ApiResponse;
import microservice.boostore.userservice.dto.AuthResponse;
import microservice.boostore.userservice.dto.LoginRequest;
import microservice.boostore.userservice.dto.RefreshTokenRequest;
import microservice.boostore.userservice.dto.RegisterRequest;
import microservice.boostore.userservice.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success(authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ApiResponse.success(authService.refreshToken(request.getRefreshToken()));
    }

    /** Test endpoint — cần gửi kèm Bearer token hợp lệ */
    @GetMapping("/me")
    public ApiResponse<String> getCurrentUser(Authentication authentication) {
        return ApiResponse.success("Hello: " + authentication.getName());
    }
}
