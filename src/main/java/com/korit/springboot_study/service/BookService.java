package com.korit.springboot_study.service;

import com.korit.springboot_study.dto.request.ReqAddBookDto;
import com.korit.springboot_study.dto.request.ReqBookDto;
import com.korit.springboot_study.dto.request.ReqDeleteBookDto;
import com.korit.springboot_study.dto.request.ReqUpdateBookDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.repository.BookRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public SuccessResponseDto<List<Book>> getBooks(String bookName) throws NotFoundException {
        List<Book> foundBooks = bookRepository
                .findBooks(bookName)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 도서명입니다."));

        return new SuccessResponseDto<>(foundBooks);
    }

    public SuccessResponseDto<List<Book>> getAllBooks() throws NotFoundException {
        List<Book> foundAllBooks = bookRepository
                .findAllBooks()
                .orElseThrow(() -> new NotFoundException("존재하지 않는 도서명입니다."));

        return new SuccessResponseDto<>(foundAllBooks);
    }

    @Transactional(rollbackFor = Exception.class)
    public Book saveBook(ReqAddBookDto reqAddBookDto) throws DuplicateKeyException {
        return bookRepository.saveBook(reqAddBookDto.toBook()).get();
    }

    @Transactional(rollbackFor = Exception.class)
    public SuccessResponseDto<Book> modifyBook(int bookId, ReqUpdateBookDto reqUpdateBookDto) throws NotFoundException {
        return new SuccessResponseDto<>(bookRepository.updateBook(reqUpdateBookDto.toBook(bookId))
                .orElseThrow(() -> new NotFoundException("해당 도서는 존재하지 않습니다.")));
    }

    @Transactional(rollbackFor = Exception.class)
    public SuccessResponseDto<String> deleteBook(ReqDeleteBookDto reqDeleteBookDto) throws NotFoundException {
        return new SuccessResponseDto<>(bookRepository.deleteBook(reqDeleteBookDto.getBookName())
                .orElseThrow(() -> new NotFoundException("해당 도서는 존재하지 않습니다.")));
    }
}
