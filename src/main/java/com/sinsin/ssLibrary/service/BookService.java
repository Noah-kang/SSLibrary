package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.vo.Book;
import com.sinsin.ssLibrary.vo.Page;

public interface BookService {
    Page<Book> getBookPage(int page, int size);
    Page<Book> getBookPage(int page, int size, String field, String keyword);
    Book      getBook(int bookId);
    void      create(Book book);
    void      update(Book book);
    void      delete(int bookId);
}
