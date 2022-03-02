package com.example.application.service;

import com.example.application.model.Book;
import com.example.application.repository.BookRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Page<Book> find_paginated(Pageable pageable) {
        List<Book> books = book_repository.findAll();
        int page_size = pageable.getPageSize();
        int current_page = pageable.getPageNumber();
        int start_item = page_size * current_page;
        int index = Math.min(start_item+page_size,books.size());
        return new PageImpl<>(books.subList(start_item,index), PageRequest.of(current_page,page_size),books.size());
    }

    @Override
    public Page<Book> find_searched_paginated(Pageable pageable, String search) {
        Set<Book> allBooks = new HashSet<>();
        List<Book> books = book_repository.findByTitle(search);
        if(books != null){
            allBooks.addAll(books);
        }
        books = book_repository.findByAuthor(search);
        if(books != null){
            allBooks.addAll(books);
        }
        books = book_repository.findByCategory(search);
        if(books != null){
            allBooks.addAll(books);
        }
        int page_size = pageable.getPageSize();
        int current_page = pageable.getPageNumber();
        int start_item = page_size * current_page;
        int index = Math.min(start_item+page_size, allBooks.size());
        return new PageImpl<>(allBooks.stream().toList().subList(start_item,index),
                PageRequest.of(current_page,page_size), allBooks.size());
    }

}
