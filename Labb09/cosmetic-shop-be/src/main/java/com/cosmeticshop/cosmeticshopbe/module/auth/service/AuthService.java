package com.cosmeticshop.cosmeticshopbe.module.auth.service;

import com.cosmeticshop.cosmeticshopbe.module.auth.dto.AuthResponseDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.dto.LoginRequestDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.dto.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO refreshToken(String refreshToken);
    void logout(String refreshToken);
}
