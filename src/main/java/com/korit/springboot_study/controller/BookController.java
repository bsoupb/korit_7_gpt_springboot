package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.ReqAddBookDto;
import com.korit.springboot_study.dto.request.ReqBookDto;
import com.korit.springboot_study.dto.request.ReqDeleteBookDto;
import com.korit.springboot_study.dto.request.ReqUpdateBookDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.service.BookService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/api/book/{bookName}")
    public ResponseEntity<SuccessResponseDto<List<Book>>> getBooks(
            @RequestParam(required = false) String bookName) throws NotFoundException {
        return ResponseEntity.ok().body(bookService.getBooks(bookName));
    }

    @GetMapping("/api/book")
    public ResponseEntity<SuccessResponseDto<List<Book>>> getAllBooks() throws NotFoundException {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @PostMapping("/api/book")
    public ResponseEntity<SuccessResponseDto<Book>> saveBook(@Valid @RequestBody ReqAddBookDto reqAddBookDto) throws MethodArgumentNotValidException {
        return ResponseEntity.ok().body(new SuccessResponseDto<>(bookService.saveBook(reqAddBookDto)));
    }

    @PutMapping("/api/book/{bookId}")
    public ResponseEntity<SuccessResponseDto<Book>> modifyBook(
            @PathVariable int bookId,
            @Valid @RequestBody ReqUpdateBookDto reqUpdateBookDto) throws NotFoundException, MethodArgumentNotValidException {
        return ResponseEntity.ok().body(bookService.modifyBook(bookId, reqUpdateBookDto));
    }

    @DeleteMapping("/api/book/{bookName}")
    public ResponseEntity<SuccessResponseDto<String>> deleteBook(@Valid @RequestBody ReqDeleteBookDto reqDeleteBookDto) throws MethodArgumentNotValidException, NotFoundException {
        return ResponseEntity.ok().body(bookService.deleteBook(reqDeleteBookDto));
    }
}
