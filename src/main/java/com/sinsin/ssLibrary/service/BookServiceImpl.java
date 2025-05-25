package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.dao.BookMapper;
import com.sinsin.ssLibrary.vo.Book;
import com.sinsin.ssLibrary.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookMapper mapper) {
        this.bookMapper = mapper;
    }

    @Override
    public Page<Book> getBookPage(int page, int size) {
        return getBookPage(page, size, null, null);
    }

    @Override
    public Page<Book> getBookPage(int page, int size, String field, String keyword) {
        // 파라미터 보정
        if (keyword != null) keyword = keyword.trim();
        int total;
        List<Book> list;
        if (keyword == null || keyword.isEmpty()) {
            total = bookMapper.countBooks();
            int offset = (page - 1) * size;
            list = bookMapper.selectByPage(offset, size);
        } else {
            total = bookMapper.countByCondition(field, keyword);
            int offset = (page - 1) * size;
            list = bookMapper.selectByCondition(field, keyword, offset, size);
        }
        return new Page<>(list, page, size, total, 10);
    }

    @Override
    public Book getBook(int bookId) {
        return bookMapper.selectById(bookId);
    }

    @Override
    @Transactional
    public void create(Book book) {
        bookMapper.insertBook(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        bookMapper.updateBook(book);
    }

    @Override
    @Transactional
    public void delete(int bookId) {
        bookMapper.deleteBook(bookId);
    }
}
