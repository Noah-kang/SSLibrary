package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.vo.Member;

/**
 * 회원 관련 비즈니스 로직 인터페이스
 */
public interface MemberService {
    /**
     * 로그인 인증 처리
     *
     * @param email    로그인 아이디(이메일)
     * @param password 로그인 비밀번호
     * @return 인증 성공 시 Member 객체, 실패 시 null
     */
    Member authenticate(String email, String password);

    /**
     * 신규 회원 등록
     *
     * @param member 가입할 회원 정보
     */
    void register(Member member);
}
