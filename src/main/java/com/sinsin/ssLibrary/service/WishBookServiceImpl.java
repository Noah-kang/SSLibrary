package com.sinsin.ssLibrary.service;

import com.sinsin.ssLibrary.dao.WishBookMapper;
import com.sinsin.ssLibrary.vo.Page;
import com.sinsin.ssLibrary.vo.WishBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishBookServiceImpl implements WishBookService {
    private final WishBookMapper mapper;

    @Autowired
    public WishBookServiceImpl(WishBookMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Page<WishBook> getWishPageByMember(int page, int size, int memberId) {
        int total = mapper.countByMember(memberId);
        int offset = (page - 1) * size;
        List<WishBook> list = mapper.selectByMember(memberId, offset, size);
        int blockSize = 10;
        return new Page<>(list, page, size, total, blockSize);
    }

    @Override
    @Transactional
    public void create(WishBook wish) {
        mapper.insertWish(wish);
    }

    @Override
    @Transactional
    public void update(WishBook wish) {
        mapper.updateWish(wish);
    }

    @Override
    @Transactional
    public void delete(int wishId) {
        mapper.deleteById(wishId);
    }

    @Override
    public WishBook getById(int wishId) {
        return mapper.selectById(wishId);
    }

    @Override
    public Page<WishBook> getWishPage(int page, int size) {
        int total = mapper.countAll();
        int offset = (page - 1) * size;
        List<WishBook> list = mapper.selectAll(offset, size);
        return new Page<>(list, page, size, total, 10);
    }
}
