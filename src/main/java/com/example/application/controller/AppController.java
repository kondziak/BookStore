package com.example.application.controller;

import com.example.application.model.Billing;
import com.example.application.model.Book;
import com.example.application.service.BookService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String getStartPage(){
        return "index";
    }

    @GetMapping("/home")
    public String getMainPage(@RequestParam(value = "page",required = false)Optional<Integer> page,
                                @RequestParam(value = "size",required = false) Optional<Integer> size, Model model){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Book> bookPages = bookService.find_paginated(PageRequest.of(currentPage-1,pageSize));
        model.addAttribute("book_pages",bookPages);

        int totalPages = bookPages.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumber = IntStream.range(1,totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers",pageNumber);
        }

        return "home";
    }

    @GetMapping("/add_address")
    public String getAddress(Model model){
        model.addAttribute("billing", new Billing());
        return "add_address";
    }

    @GetMapping("/edit_data")
    public String getUserData(){
        return "edit_data";
    }

}
