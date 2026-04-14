package microservice.boostore.userservice.service;

import lombok.RequiredArgsConstructor;
import microservice.boostore.userservice.dto.UserResponse;
import microservice.boostore.userservice.entity.UserEntity;
import microservice.boostore.userservice.repository.UserRepository;
import microservice.bookstore.common.exception.AppException;
import microservice.bookstore.common.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /** Lấy toàn bộ danh sách user — chỉ dành cho ADMIN */
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /** Lấy thông tin 1 user theo ID — chỉ dành cho ADMIN */
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return toResponse(user);
    }

    private UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
