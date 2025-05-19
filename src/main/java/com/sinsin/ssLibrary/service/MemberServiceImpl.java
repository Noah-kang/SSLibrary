package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.dao.MemberMapper;
import com.sinsin.ssLibrary.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberService 구현체
 */
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Member authenticate(String email, String password) {
        return memberMapper.selectByEmailAndPassword(email, password);
    }

    @Override
    @Transactional
    public void register(Member member) {
        memberMapper.insertMember(member);
    }
}
