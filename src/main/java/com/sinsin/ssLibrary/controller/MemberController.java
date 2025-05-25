package com.sinsin.ssLibrary.controller;

import com.sinsin.ssLibrary.service.MemberService;
import com.sinsin.ssLibrary.vo.Member;
import com.sinsin.ssLibrary.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

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
            return "redirect:/admin/books";   // 기존 /admin/home → /admin/books 로 변경
        } else {
            return "redirect:/user/books";
        }
    }

    // **추가**: 관리자 홈 뷰 매핑
    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        // 기본 활성 탭을 도서관리로 설정
        model.addAttribute("activeTab", "books");
        return "admin/home";
    }

    // (필요시) 사용자 홈 매핑도 추가
    @GetMapping("/user/home")
    public String userHome(Model model) {
        // 사용자 홈 로직...
        return "user/home";
    }

    // 회원관리 목록
    @GetMapping("/admin/members")
    public String listMembers(
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        // 1) 어떤 탭을 활성화할지
        model.addAttribute("activeTab", "members");

        // 2) 페이징된 데이터 준비
        Page<Member> pg = memberService.getMembersPage(page, 10);
        model.addAttribute("pageData", pg);
        model.addAttribute("members", pg.getItems());

        // 3) 최종 뷰 이름은 언제나 admin/home
        return "admin/home";
    }

    /** 회원 등록 폼 */
    @GetMapping("/admin/members/new")
    public String newForm(Model model) {
        model.addAttribute("activeTab", "members");
        model.addAttribute("mode", "new");
        model.addAttribute("member", new Member());
        return "admin/home";
    }

    /** 회원 등록 처리 */
    @PostMapping("/admin/members")
    public String create(Member member) {
        member.setRole(member.getRole() == null ? "USER" : member.getRole());
        memberService.register(member);
        return "redirect:/admin/members";
    }

    /** 회원 수정 폼 */
    @GetMapping("/admin/members/{id}/edit")
    public String editForm(
            @PathVariable("id") int id,
            Model model) {
        model.addAttribute("activeTab", "members");
        model.addAttribute("mode", "edit");
        model.addAttribute("member", memberService.getMemberById(id));
        return "admin/home";
    }

    /** 회원 수정 처리 */
    @PostMapping("/admin/members/{id}/edit")
    public String update(
            @PathVariable("id") int id,
            Member member) {
        member.setMemberId(id);
        memberService.update(member);
        return "redirect:/admin/members";
    }

    /** 회원 삭제 처리 */
    @PostMapping("/admin/members/{id}/delete")
    public String delete(@PathVariable int id) {
        memberService.delete(id);
        return "redirect:/admin/members";
    }

    // 로그아웃 (POST 권장)
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
