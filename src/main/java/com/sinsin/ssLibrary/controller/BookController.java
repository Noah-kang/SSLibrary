package com.sinsin.ssLibrary.controller;

import com.sinsin.ssLibrary.service.BookService;
import com.sinsin.ssLibrary.vo.Book;
import com.sinsin.ssLibrary.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService svc) {
        this.bookService = svc;
    }

    /** 도서관리 메인 (페이징) */
    @GetMapping("/admin/books")
    public String list(
            @RequestParam(defaultValue="1") int page,
            Model model
    ) {
        model.addAttribute("activeTab", "books");
        Page<Book> pg = bookService.getBookPage(page, 10);
        model.addAttribute("pageData", pg);
        model.addAttribute("books", pg.getItems());
        return "admin/home";
    }

    /** 도서 등록 폼 */
    @GetMapping("/admin/books/new")
    public String newForm(Model model) {
        model.addAttribute("activeTab", "books");
        model.addAttribute("mode", "new");
        model.addAttribute("book", new Book());
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
