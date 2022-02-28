package com.example.application.controller;

import com.example.application.model.Book;
import com.example.application.repository.BookRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {

    private final BookRepository book_repository;

    public AppController(@NonNull @Lazy BookRepository book_repository) {
        super();
        this.book_repository = book_repository;
    }

    @GetMapping("/")
    public String get_start_page(){
        return "index";
    }

    @GetMapping("/home")
    public String get_main_page(Model model){
        List<Book> books = book_repository.findAll();
        if(books.size() > 0){
            model.addAttribute("books",books);
        }
        return "home";
    }




}
