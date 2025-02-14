package com.korit.springboot_study.dto.request;

import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.entity.Publisher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqAddBookDto {
    @ApiModelProperty(value = "책 이름", example = "홍길동전")
    private String bookName;
    @ApiModelProperty(value = "저자 ID", example = "1")
    private int authorId;
    @ApiModelProperty(value = "isbn", example = "A1234567890")
    private String isbn;
    @ApiModelProperty(value = "카테고리 ID", example = "1")
    private int categoryId;
    @ApiModelProperty(value = "출판사 ID", example = "1")
    private int publisherId;
    @ApiModelProperty(value = "책 이미지 url", example = "https://ebook.seocholib.or.kr/upload/20553/content/ebook/4801197112820/L4801197112820.jpg")
    private String bookImgUrl;

    public Book toBook() {
        return Book.builder()
                .bookName(bookName)
                .authorId(authorId)
                .isbn(isbn)
                .categoryId(categoryId)
                .publisherId(publisherId)
                .bookImgUrl(bookImgUrl)
                .build();
    }
}
