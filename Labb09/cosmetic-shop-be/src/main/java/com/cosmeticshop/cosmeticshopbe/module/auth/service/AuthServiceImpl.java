package com.cosmeticshop.cosmeticshopbe.module.auth.service;

import com.cosmeticshop.cosmeticshopbe.module.auth.dto.AuthResponseDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.dto.LoginRequestDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.dto.RegisterRequestDTO;
import com.cosmeticshop.cosmeticshopbe.module.auth.entity.Account;
import com.cosmeticshop.cosmeticshopbe.module.auth.entity.RefreshToken;
import com.cosmeticshop.cosmeticshopbe.module.auth.repository.AccountRepository;
import com.cosmeticshop.cosmeticshopbe.module.auth.repository.RefreshTokenRepository;
import com.cosmeticshop.cosmeticshopbe.shared.config.security.CustomUserDetails;
import com.cosmeticshop.cosmeticshopbe.shared.config.security.JwtTokenProvider;
import com.cosmeticshop.cosmeticshopbe.shared.enums.AccountRole;
import com.cosmeticshop.cosmeticshopbe.shared.enums.AccountStatus;
import com.cosmeticshop.cosmeticshopbe.shared.enums.AuthProvider;
import com.cosmeticshop.cosmeticshopbe.shared.exception.AppException;
import com.cosmeticshop.cosmeticshopbe.shared.exception.ErrorCode;
import com.cosmeticshop.cosmeticshopbe.shared.utils.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    @Value("${jwt.refresh-token-expiration:604800000}")
    private long refreshExpirationInMs;

    public AuthServiceImpl(AccountRepository accountRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder,
                           RedisService redisService) {
        this.accountRepository = accountRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
    }

    @Override
    @Transactional
    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = userDetails.getAccount();

        account.setLastLoginAt(LocalDateTime.now());
        accountRepository.save(account);

        return generateAuthResponse(account, userDetails);
    }

    @Override
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Account account = Account.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .role(AccountRole.CUSTOMER)
                .status(AccountStatus.ACTIVE)
                .provider(AuthProvider.LOCAL)
                .emailVerified(true)
                .build();

        accountRepository.save(account);

        CustomUserDetails userDetails = new CustomUserDetails(account);
        return generateAuthResponse(account, userDetails);
    }

    @Override
    @Transactional
    public AuthResponseDTO refreshToken(String requestRefreshToken) {
        String key = "rt:" + requestRefreshToken;
        String accountIdStr = redisService.get(key);
        
        if (accountIdStr == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Refresh token is invalid or expired in cache");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED, "Refresh token not found in DB"));

        if (refreshToken.getIsRevoked() || refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Refresh token was expired or revoked");
        }

        Account account = refreshToken.getAccount();
        CustomUserDetails userDetails = new CustomUserDetails(account);

        String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);

        return AuthResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .role(account.getRole().name())
                .build();
    }

    @Override
    @Transactional
    public void logout(String requestRefreshToken) {
        String key = "rt:" + requestRefreshToken;
        redisService.delete(key);

        refreshTokenRepository.findByToken(requestRefreshToken).ifPresent(rt -> {
            rt.setIsRevoked(true);
            refreshTokenRepository.save(rt);
        });
    }

    private AuthResponseDTO generateAuthResponse(Account account, CustomUserDetails userDetails) {
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshTokenString = jwtTokenProvider.generateRefreshToken(account.getId());

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .account(account)
                .token(refreshTokenString)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshExpirationInMs / 1000))
                .isRevoked(false)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        redisService.set("rt:" + refreshTokenString, account.getId().toString(), refreshExpirationInMs, TimeUnit.MILLISECONDS);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenString)
                .email(account.getEmail())
                .fullName(account.getFullName())
                .role(account.getRole().name())
                .build();
    }
}
