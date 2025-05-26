package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.vo.Page;
import com.sinsin.ssLibrary.vo.WishBook;

import java.util.List;

public interface WishBookService {
    Page<WishBook> getWishPageByMember(int page, int size, int memberId);
    void create(WishBook wish);
    void update(WishBook wish);
    void delete(int wishId);
    /** 단건 조회 */
    WishBook getById(int wishId);
    Page<WishBook> getWishPage(int page, int size);

}
