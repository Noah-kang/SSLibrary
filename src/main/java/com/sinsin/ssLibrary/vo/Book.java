package com.sinsin.ssLibrary.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
    private Integer bookId;
    private String  title;
    private String  author;
    private String  location;
    private String  note;
    private Integer category1Id;
    private Integer category2Id;
}
