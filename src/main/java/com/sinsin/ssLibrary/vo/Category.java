package com.sinsin.ssLibrary.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {
    private Integer categoryId;
    private String  name;
    private Integer parentId;
    // 부모 카테고리 이름 출력용
    private String  parentName;
}
