package com.example.application.controller;

import com.example.application.model.Book;
import com.example.application.service.BookService;
import lombok.NonNull;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AppController {

    private final BookService bookService;


    public AppController(@NonNull @Lazy BookService bookService) {
        super();
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String get_start_page(){
        return "index";
    }

    @GetMapping("/home")
    public String get_main_page(@RequestParam(value = "page",required = false)Optional<Integer> page,
                                @RequestParam(value = "size",required = false) Optional<Integer> size, Model model){
        int current_page = page.orElse(1);
        int page_size = size.orElse(5);

        Page<Book> book_pages = bookService.find_paginated(PageRequest.of(current_page-1,page_size));
        model.addAttribute("book_pages",book_pages);

        int total_pages = book_pages.getTotalPages();
        if(total_pages > 0){
            List<Integer> page_num = IntStream.range(1,total_pages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers",page_num);
        }

        return "home";
    }




}
