package com.sinsin.ssLibrary.dao;

import com.sinsin.ssLibrary.vo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    /** 도서 목록 (페이징 안 할 때) */
    List<Book> selectAll();

    /** 검색없는 기존 도서 페이징 */
    List<Book> selectByPage(@Param("offset") int offset, @Param("limit") int limit);
    int countBooks();

    // 검색용 메서드
    int countByCondition(@Param("field") String field,
                         @Param("keyword") String keyword);
    List<Book> selectByCondition(
            @Param("field") String field,
            @Param("keyword") String keyword,
            @Param("offset") int offset,
            @Param("limit")  int limit
    );

    /** 단일 도서 조회 */
    Book selectById(@Param("bookId") int bookId);

    /** 도서 등록 */
    void insertBook(Book book);

    /** 도서 수정 */
    void updateBook(Book book);

    /** 도서 삭제 */
    void deleteBook(@Param("bookId") int bookId);
}
