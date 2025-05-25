package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.dao.CategoryMapper;
import com.sinsin.ssLibrary.vo.Category;
import com.sinsin.ssLibrary.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Page<Category> getCategoryPage(int page, int size) {
        int total   = mapper.countCategories();
        int offset  = (page - 1) * size;
        List<Category> list = mapper.selectByPage(offset, size);
        // blockSize 10 적용
        return new Page<>(list, page, size, total, 10);
    }

    @Override
    public List<Category> getAll() {
        return mapper.selectAll();
    }

    @Override
    public Category getById(int id) {
        return mapper.selectById(id);
    }

    @Override
    @Transactional
    public void create(Category category) {
        mapper.insertCategory(category);
    }

    @Override
    @Transactional
    public void update(Category category) {
        mapper.updateCategory(category);
    }

    @Override
    @Transactional
    public void delete(int id) {
        mapper.deleteCategory(id);
    }
}
