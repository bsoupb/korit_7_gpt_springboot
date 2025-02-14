package com.korit.springboot_study.repository;

import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import com.korit.springboot_study.mapper.BookMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepository {

    @Autowired
    private BookMapper bookMapper;

    public Optional<List<Book>> findBooks(String bookName) throws NotFoundException {
        List<Book> foundBooks = bookMapper.selectBooks(bookName);
        return foundBooks.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundBooks);
    }

    public Optional<List<Book>> findAllBooks() throws NotFoundException {
        List<Book> foundAllBooks = bookMapper.selectAllBooks();
        return foundAllBooks.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundAllBooks);
    }

    public Optional<Book> saveBook(Book book) throws DuplicateKeyException {
        try {
            int insertedBook = bookMapper.insertBook(book);
        } catch(DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("bookName", "중복된 도서명 입니다.")
            );
        }
        return Optional.of(book);
    }

    public Optional<Book> updateBook(Book book) throws NotFoundException, DuplicateKeyException {
        try {
            if(bookMapper.updateBook(book.getBookId(), book.getBookName()) < 1) {
                return Optional.empty();
            }
        } catch(DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("bookName", "중복된 도서명입니다.")
            );
        }
        return Optional.ofNullable(book);
    }

    public Optional<String> deleteBook(String bookName) throws NotFoundException, DuplicateKeyException {
        try {
            if(bookMapper.deleteBook(bookName) < 1) {
                return Optional.empty();
            }
        } catch(DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("bookName", "중복된 도서입니다.")
            );
        }
        return Optional.ofNullable(bookName);
    }
}
