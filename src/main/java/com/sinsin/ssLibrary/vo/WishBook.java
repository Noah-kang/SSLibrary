package com.sinsin.ssLibrary.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class WishBook {
    private Integer    wishId;
    private Integer    memberId;
    private String     title;
    private String     author;
    private LocalDateTime createdAt;
    // 사용자 이름 표시용, 조인된 회원 이름
    private String     memberName;

    // >>> 추가 ▶ 생성일시를 포맷팅해서 반환
    public String getCreatedAtStr() {
        if (createdAt == null) return "";
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
