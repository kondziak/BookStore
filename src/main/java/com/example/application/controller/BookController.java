package com.example.application.controller;

import com.example.application.model.Book;
import com.example.application.repository.BookRepository;
import com.example.application.service.BookService;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/book_action")
public class BookController {

    private final BookRepository book_repository;
    private final BookService book_service;

    public BookController(@NonNull @Lazy BookRepository book_repository,@NonNull @Lazy BookService book_service) {
        super();
        this.book_repository = book_repository;
        this.book_service = book_service;
    }

    @ModelAttribute("book")
    Book create_book(){
        return new Book();
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    private String add_book(@ModelAttribute("book") Book book){
       book_repository.save(book);
        try{
            byte[] bytes = book.getBook_image().getBytes();
            String name = "src/main/resources/static/images/books/" + book.getId() + ".png";
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(name));
            stream.write(bytes);
            stream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/home";
    }

    @PostMapping(value = "/remove_book")
    private String remove_book(@RequestParam(value="id") Long id){
        String name = "src/main/resources/static/images/books/" + id + ".png";
        try{
            File file = new File(name);
            if(file.exists() && !file.isDirectory()){
                file.delete();
            }
            book_repository.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/book_action/show_remove";
    }

    @PostMapping("/search")
    private String search_array(@RequestParam(value = "search") String searching_value, Model model){
        Page<Book> book_pages = book_service.find_searched_paginated(PageRequest.of(0,5),searching_value);
        model.addAttribute("book_pages",book_pages);

        if(book_pages.getTotalPages() > 0){
            List<Integer> page_num = IntStream.rangeClosed(1,book_pages.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers",page_num);
        }
        return "home";
    }

    @GetMapping("/show_remove")
    private String show_remove_form(Model model){
        List<Book> books = book_repository.findAll();
        model.addAttribute("books",books);
        return "remove_book";
    }

    @GetMapping("/show_add")
    private String show_add_form(Model model){
        model.addAttribute("book",new Book());
        return "add_book";
    }

    @GetMapping("/show_add_book")
    private String show_add_book(Model model){
        model.addAttribute("book", new Book());
        return "add_book_user";
    }

    @GetMapping("/book_detail")
    private String get_book_detail(@RequestParam("id") Long id,Model model){
        Book book = book_repository.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("id",id);
        return "book_detail";
    }
}
