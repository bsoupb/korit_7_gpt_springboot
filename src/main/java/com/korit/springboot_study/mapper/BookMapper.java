package com.korit.springboot_study.mapper;

import com.korit.springboot_study.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> selectBooks(String bookName);
    List<Book> selectAllBooks();

    int insertBook(Book book);
    int updateBook(@Param(value = "bookId") int bookId, @Param(value = "bookName") String bookName);
    int deleteBook(@Param(value = "bookName") String bookName);
}
