package com.sinsin.ssLibrary.vo;

import java.util.List;

/**
 * 페이징 처리 결과를 담는 제네릭 Page 객체
 * @param <T> 리스트 아이템 타입
 */
public class Page<T> {
    private final List<T> items;       // 페이지에 표시할 데이터
    private final int pageNumber;      // 현재 페이지 번호 (1-based)
    private final int pageSize;        // 페이지 크기 (한 페이지당 아이템 수)
    private final long totalItems;     // 전체 아이템 수
    private final int totalPages;      // 전체 페이지 수

    // 블록(한 번에 보여줄 페이지 버튼 개수)
    private final int blockSize;
    private final int startPage;
    private final int endPage;

    /**
     * 생성자: 총 아이템 수를 바탕으로 전체 페이지 수를 계산
     */
    public Page(List<T> items, int pageNumber, int pageSize, long totalItems, int blockSize) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = (int) ((totalItems + pageSize - 1) / pageSize);

        this.blockSize  = blockSize;
        int currentBlock = (pageNumber - 1) / blockSize;
        this.startPage   = currentBlock * blockSize + 1;
        this.endPage     = Math.min(startPage + blockSize - 1, totalPages);

    }

    public List<T> getItems() {
        return items;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int    getBlockSize()   { return blockSize; }
    public int    getStartPage()   { return startPage; }
    public int    getEndPage()     { return endPage; }


    /**
     * 이전 페이지가 있는지 여부
     */
    public boolean hasPrevious() {
        return pageNumber > 1;
    }

    /**
     * 다음 페이지가 있는지 여부
     */
    public boolean hasNext() {
        return pageNumber < totalPages;
    }

    public boolean hasPreviousBlock(){ return startPage > 1; }
    public boolean hasNextBlock()    { return endPage < totalPages; }
}