package com.example.application.repository;

import com.example.application.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByTitle(String title);
    void deleteById(Long aLong);
}
