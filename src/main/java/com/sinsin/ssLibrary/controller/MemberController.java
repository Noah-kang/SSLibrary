package com.sinsin.ssLibrary.controller;

import com.sinsin.ssLibrary.service.MemberService;
import com.sinsin.ssLibrary.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 회원 로그인 및 세션 관리를 위한 컨트롤러
 */
@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 로그인 페이지 폼
     * "/" 또는 "/login" 으로 접근 시 모두 같은 뷰를 반환
     */
    @GetMapping({ "/", "/login" })
    public String loginForm(HttpSession session) {
        // 이미 로그인된 상태라면 바로 홈으로 리다이렉트
        if (session.getAttribute("loginMember") != null) {
            Member m = (Member) session.getAttribute("loginMember");
            return "redirect:" + ("ADMIN".equals(m.getRole()) ? "/admin/home" : "/user/home");
        }
        return "login";  // /WEB-INF/views/login.jsp 포워딩
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        Member member = memberService.authenticate(email, password);
        if (member == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
        if (member.isBlocked()) {
            model.addAttribute("error", "차단된 계정입니다.");
            return "login";
        }
        // 세션에 로그인 회원 정보 저장
        session.setAttribute("loginMember", member);

        // role에 따른 페이지 이동
        if ("ADMIN".equals(member.getRole())) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/user/home";
        }
    }

    /**
     * 로그아웃 처리
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
