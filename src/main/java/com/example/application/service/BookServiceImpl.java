package com.example.application.service;

import com.example.application.model.Book;
import com.example.application.repository.BookRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository book_repository;

    public BookServiceImpl(@NonNull @Lazy BookRepository book_repository) {
        super();
        this.book_repository = book_repository;
    }

    @Override
    public Book save(Book book) {
        return book_repository.save(book);
    }

}
