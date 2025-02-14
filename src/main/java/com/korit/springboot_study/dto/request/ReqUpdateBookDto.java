package com.korit.springboot_study.dto.request;

import com.korit.springboot_study.entity.Book;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReqUpdateBookDto {
    @ApiModelProperty(value = "책 이름", example = "홍길동전")
    private String bookName;

    public Book toBook(int bookId) {
        return Book.builder()
                .bookId(bookId)
                .bookName(bookName)
                .build();
    }

}
