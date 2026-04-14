package microservice.boostore.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Lưu vào Redis với prefix "refresh_token"
@RedisHash("refresh_token")
public class RefreshToken {

    @Id
    private String id; // Chính là chuỗi refresh token

    private String username;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expirationMs; // Thời gian sống của token trong Redis
}
