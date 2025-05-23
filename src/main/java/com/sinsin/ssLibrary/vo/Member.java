package com.sinsin.ssLibrary.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Member 도메인 클래스
 */
@Data
@NoArgsConstructor
public class Member {
    private int memberId;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String role;
    private boolean isBlocked;
    private LocalDateTime createdAt;

    /** view 용으로 포맷된 문자열을 돌려줌 */
    public String getCreatedAtFormatted() {
        if (createdAt == null) return "";
        return createdAt.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );
    }
}
