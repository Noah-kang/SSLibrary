package com.sinsin.ssLibrary.controller;

import com.sinsin.ssLibrary.service.CategoryService;
import com.sinsin.ssLibrary.vo.Category;
import com.sinsin.ssLibrary.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    /** 카테고리 목록 + 페이징 */
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        model.addAttribute("activeTab", "categories");
        Page<Category> pg = service.getCategoryPage(page, 10);
        model.addAttribute("pageData", pg);
        model.addAttribute("categories", pg.getItems());
        return "admin/home";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("activeTab", "categories");
        model.addAttribute("mode", "new");
        model.addAttribute("category", new Category());
        // 최상위 카테고리(부모 없는 것)만 골라서 전달
        List<Category> roots = service.getAll().stream()
                .filter(c -> c.getParentId() == null)
                .collect(Collectors.toList());
        model.addAttribute("parents", roots);
        return "admin/home";
    }

    @PostMapping
    public String create(Category category) {
        service.create(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable int id,
            Model model) {
        model.addAttribute("activeTab", "categories");
        model.addAttribute("mode", "edit");
        model.addAttribute("category", service.getById(id));
        // 최상위 카테고리(부모 없는 것)만 골라서 전달
        List<Category> roots = service.getAll().stream()
                .filter(c -> c.getParentId() == null)
                .collect(Collectors.toList());
        model.addAttribute("parents", roots);
        return "admin/home";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable int id,
            Category category) {
        category.setCategoryId(id);
        service.update(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/admin/categories";
    }
}
