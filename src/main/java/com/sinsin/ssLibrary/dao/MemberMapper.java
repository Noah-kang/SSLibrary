package com.sinsin.ssLibrary.dao;

import com.sinsin.ssLibrary.vo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Member 테이블과 매핑되는 MyBatis Mapper 인터페이스
 */
@Mapper
public interface MemberMapper {

    /**
     * 이메일과 비밀번호로 회원 조회 (로그인용)
     *
     * @param email    로그인 아이디(이메일)
     * @param password 로그인 비밀번호
     * @return 일치하는 회원 정보 (없으면 null)
     */
    Member selectByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password
    );

    /**
     * 신규 회원 등록
     *
     * @param member 가입할 회원 정보가 담긴 VO 객체
     */
    void insertMember(Member member);

    // 필요에 따라 update, delete 등 추가 메서드 정의 가능

    // 모든멤버 페이지네이션
    List<Member> selectMembersByPage(
            @Param("offset") int offset,
            @Param("limit")  int limit
    );

    // 전체 멤버 세기
    int countMembers();

    /** 회원 1명 조회 */
    Member selectById(@Param("memberId") int memberId);

    /** 회원 정보 수정 */
    void updateMember(Member member);

    /** 회원 삭제 */
    void deleteMember(@Param("memberId") int memberId);
}
