package com.sinsin.ssLibrary.controller;

import com.sinsin.ssLibrary.service.WishBookService;
import com.sinsin.ssLibrary.vo.Page;
import com.sinsin.ssLibrary.vo.WishBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class WishBookController {
    private final WishBookService service;

    @Autowired
    public WishBookController(WishBookService service) {
        this.service = service;
    }

    // 사용자: 목록 조회 (HttpSession 사용)
    @GetMapping("/user/wishBooks")
    public String userList(
            @RequestParam(defaultValue = "1") int page,
            Model model,
            HttpSession session
    ) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        model.addAttribute("activeTab", "wish");
        Page<WishBook> pg = service.getWishPageByMember(page, 10, memberId);
        model.addAttribute("pageData", pg);
        model.addAttribute("wishes", pg.getItems());
        return "user/home";
    }

    /** 새 신청 폼 */
    @GetMapping("/user/wishBooks/new")
    public String newForm(Model model, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        if (memberId == null) return "redirect:/login";

        model.addAttribute("activeTab", "wish");
        model.addAttribute("mode", "new");
        model.addAttribute("wish", new WishBook());
        return "user/home";
    }

    /** 새 신청 처리 */
    @PostMapping("/user/wishBooks/new")
    public String create(@ModelAttribute WishBook wish, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        if (memberId == null) return "redirect:/login";

        wish.setMemberId(memberId);
        service.create(wish);
        return "redirect:/user/wishBooks";
    }

    /** 수정 폼 */
    @GetMapping("/user/wishBooks/{wishId}/edit")
    public String editForm(
            @PathVariable int wishId,
            Model model,
            HttpSession session
    ) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        if (memberId == null) return "redirect:/login";

        WishBook wish = service.getById(wishId);
        if (wish == null || !wish.getMemberId().equals(memberId)) {
            return "redirect:/user/wishBooks";
        }

        model.addAttribute("activeTab", "wish");
        model.addAttribute("mode", "edit");
        model.addAttribute("wish", wish);
        return "user/home";
    }

    /** 수정 처리 */
    @PostMapping("/user/wishBooks/{wishId}/edit")
    public String update(
            @PathVariable int wishId,
            @ModelAttribute WishBook wish,
            HttpSession session
    ) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        if (memberId == null) return "redirect:/login";

        wish.setWishId(wishId);
        wish.setMemberId(memberId);
        service.update(wish);
        return "redirect:/user/wishBooks";
    }

    @PostMapping("/user/wishBooks/{wishId}/delete")
    public String deleteWish(
            @PathVariable int wishId,
            HttpSession session
    ) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        service.delete(wishId);
        return "redirect:/user/wishBooks";
    }

    @GetMapping("/admin/wishBooks")
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        Page<WishBook> pg = service.getWishPage(page, 10);
        model.addAttribute("activeTab", "wishBooks");
        model.addAttribute("pageData", pg);
        model.addAttribute("wishes", pg.getItems());
        return "admin/home";
    }

    @GetMapping("/admin/wishBooks/{wishId}/edit")
    public String editForm(@PathVariable int wishId, Model model) {
        WishBook wish = service.getById(wishId);
        model.addAttribute("activeTab", "wishBooks");
        model.addAttribute("mode", "edit");
        model.addAttribute("wish", wish);
        return "admin/home";
    }

    @PostMapping("/admin/wishBooks/{wishId}/edit")
    public String update(@PathVariable int wishId, @ModelAttribute WishBook wish) {
        wish.setWishId(wishId);
        service.update(wish);
        return "redirect:/admin/wishBooks";
    }

    @PostMapping("/admin/wishBooks/{wishId}/delete")
    public String delete(@PathVariable int wishId) {
        service.delete(wishId);
        return "redirect:/admin/wishBooks";
    }
}
