package com.sinsin.ssLibrary.dao;

import com.sinsin.ssLibrary.vo.WishBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WishBookMapper {
    int countByMember(@Param("memberId") int memberId);

    List<WishBook> selectByMember(
            @Param("memberId") int memberId,
            @Param("offset")   int offset,
            @Param("limit")    int limit
    );

    void insertWish(WishBook wish);
    void updateWish(WishBook wish);
    void deleteById(@Param("wishId") int wishId);
    /** 단건 조회 */
    WishBook selectById(@Param("wishId") int wishId);

    int countAll();

    List<WishBook> selectAll(
            @Param("offset") int offset,
            @Param("limit")  int limit
    );
}