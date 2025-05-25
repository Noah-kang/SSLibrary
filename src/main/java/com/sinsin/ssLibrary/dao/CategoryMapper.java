package com.sinsin.ssLibrary.dao;

import com.sinsin.ssLibrary.vo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectAll();
    Category       selectById(@Param("categoryId") int categoryId);
    void           insertCategory(Category category);
    void           updateCategory(Category category);
    void           deleteCategory(@Param("categoryId") int categoryId);
    int countCategories();      // 전체 카테고리 수
    List<Category> selectByPage(
            @Param("offset") int offset,
            @Param("limit")  int limit
    );                          // 페이징 조회
}
