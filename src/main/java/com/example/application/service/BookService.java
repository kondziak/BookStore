package com.example.application.service;

import com.example.application.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book save(Book book);
    Page<Book>find_paginated(Pageable pageable);
    Page<Book>find_searched_paginated(Pageable pageable, String search);
    Book getBookById(Long id);
}
