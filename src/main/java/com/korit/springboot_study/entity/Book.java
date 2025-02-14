package com.korit.springboot_study.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int bookId;
    private String bookName;
    private int authorId;
    private String isbn;
    private int categoryId;
    private int publisherId;
    private String bookImgUrl;

    private Author author;
    private Category category;
    private Publisher publisher;
}
