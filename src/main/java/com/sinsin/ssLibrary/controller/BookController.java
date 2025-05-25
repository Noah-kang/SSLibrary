package com.sinsin.ssLibrary.controller;

import com.sinsin.ssLibrary.service.BookService;
import com.sinsin.ssLibrary.service.CategoryService;
import com.sinsin.ssLibrary.vo.Book;
import com.sinsin.ssLibrary.vo.Category;
import com.sinsin.ssLibrary.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;  // 추가

    @Autowired
    public BookController(
            BookService bookService,
            CategoryService categoryService
    ) {
        this.bookService = bookService;
        this.categoryService = categoryService;  // 주입
    }

    /** 도서관리 메인 (페이징) */
    @GetMapping("/admin/books")
    public String list(
            @RequestParam(defaultValue="1")        int page,
            @RequestParam(required=false)           String field,
            @RequestParam(required=false)           String keyword,
            Model model
    ) {
        model.addAttribute("activeTab", "books");

        // 검색 폼용 정보
        model.addAttribute("field",   field);
        model.addAttribute("keyword", keyword);

        // 페이징 + 검색 결과
        Page<Book> pg = bookService.getBookPage(page, 10, field, keyword);
        model.addAttribute("pageData", pg);
        model.addAttribute("books",    pg.getItems());
        return "admin/home";
    }

    /**
     * 사용자용 도서목록 (검색 + 페이징)
     * URL 예시: /user/books?page=1&field=title&keyword=Java
     */
    @GetMapping("/user/books")
    public String userlist(
            @RequestParam(defaultValue = "1")      int page,
            @RequestParam(required = false)        String field,
            @RequestParam(required = false)        String keyword,
            Model model
    ) {
        // 탭 활성화
        model.addAttribute("activeTab", "books");
        // 검색 폼 값 유지
        model.addAttribute("field",     field);
        model.addAttribute("keyword",   keyword);

        // 서비스 호출: 검색+페이징
        Page<Book> pg = bookService.getBookPage(page, 10, field, keyword);
        model.addAttribute("pageData", pg);
        model.addAttribute("books",    pg.getItems());

        // user/home.jsp 에서 user/books.jsp를 include 하도록
        return "user/home";
    }

    /** 도서 등록 폼 */
    @GetMapping("/admin/books/new")
    public String newForm(Model model) {
        model.addAttribute("activeTab", "books");
        model.addAttribute("mode", "new");
        model.addAttribute("book", new Book());
        // 1차 카테고리(부모 없음)만
        List<Category> parents = categoryService.getAll().stream()
                .filter(c -> c.getParentId() == null)
                .collect(Collectors.toList());
        model.addAttribute("parentCategories", parents);
        // 2차 카테고리 전체 (JS에서 필터링)
        model.addAttribute("allCategories", categoryService.getAll());
        return "admin/home";
    }

    /** 도서 등록 처리 */
    @PostMapping("/admin/books")
    public String create(Book book) {
        bookService.create(book);
        return "redirect:/admin/books";
    }

    /** 도서 수정 폼 */
    @GetMapping("/admin/books/{id}/edit")
    public String editForm(
            @PathVariable("id") int id,
            Model model
    ) {
        model.addAttribute("activeTab", "books");
        model.addAttribute("mode", "edit");
        model.addAttribute("book", bookService.getBook(id));
        // 1차 카테고리(부모 없음)만
        List<Category> parents = categoryService.getAll().stream()
                .filter(c -> c.getParentId() == null)
                .collect(Collectors.toList());
        model.addAttribute("parentCategories", parents);
        // 2차 카테고리 전체 (JS에서 필터링)
        model.addAttribute("allCategories", categoryService.getAll());
        return "admin/home";
    }

    /** 도서 수정 처리 */
    @PostMapping("/admin/books/{id}/edit")
    public String update(
            @PathVariable("id") int id,
            Book book
    ) {
        book.setBookId(id);
        bookService.update(book);
        return "redirect:/admin/books";
    }

    /** 도서 삭제 처리 */
    @PostMapping("/admin/books/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/admin/books";
    }
}
