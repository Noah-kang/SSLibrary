package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.vo.Category;
import com.sinsin.ssLibrary.vo.Page;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category       getById(int id);
    void           create(Category category);
    void           update(Category category);
    void           delete(int id);
    Page<Category> getCategoryPage(int page, int size);
}
