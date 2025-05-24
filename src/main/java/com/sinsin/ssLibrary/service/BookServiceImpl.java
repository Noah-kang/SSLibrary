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
        int total = bookMapper.countBooks();
        int offset = (page - 1) * size;
        List<Book> list = bookMapper.selectByPage(offset, size);

        int blockSize = 10;
        return new Page<>(list, page, size, total, blockSize);
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
