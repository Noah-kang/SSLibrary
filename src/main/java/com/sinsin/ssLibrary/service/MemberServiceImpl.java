package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.dao.MemberMapper;
import com.sinsin.ssLibrary.vo.Member;
import com.sinsin.ssLibrary.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public Page<Member> getMembersPage(int page, int size) {
        int total = memberMapper.countMembers();
        int offset = (page - 1) * size;
        List<Member> list = memberMapper.selectMembersByPage(offset, size);
        return new Page<>(list, page, size, total);
    }

    @Override
    public Member getMemberById(int memberId) {
        return memberMapper.selectById(memberId);
    }

    @Override
    @Transactional
    public void update(Member member) {
        memberMapper.updateMember(member);
    }

    @Override
    @Transactional
    public void delete(int memberId) {
        memberMapper.deleteMember(memberId);
    }
}
