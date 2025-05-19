package com.sinsin.ssLibrary.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
}
