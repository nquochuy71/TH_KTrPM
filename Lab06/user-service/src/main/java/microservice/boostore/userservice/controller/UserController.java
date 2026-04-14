package microservice.boostore.userservice.controller;

import lombok.RequiredArgsConstructor;
import microservice.bookstore.common.dto.ApiResponse;
import microservice.boostore.userservice.dto.UserResponse;
import microservice.boostore.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** GET /api/v1/users — Lấy danh sách tất cả user (chỉ ADMIN) */
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.success(userService.getAllUsers());
    }

    /** GET /api/v1/users/{id} — Lấy thông tin 1 user theo ID (chỉ ADMIN) */
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        return ApiResponse.success(userService.getUserById(id));
    }
}
