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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/bookAction")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    public BookController(@NonNull @Lazy BookRepository book_repository,@NonNull @Lazy BookService book_service) {
        super();
        this.bookRepository = book_repository;
        this.bookService = book_service;
    }

    @ModelAttribute("book")
    Book createBook(){
        return new Book();
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    private String addBook(@ModelAttribute("book") Book book){
       bookRepository.save(book);
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

    @PostMapping(value = "/removeBook")
    private String removeBook(@RequestParam(value="id") Long id){
        String name = "src/main/resources/static/images/books/" + id + ".png";
        try{
            File file = new File(name);
            if(file.exists() && !file.isDirectory()){
                file.delete();
            }
            bookRepository.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/bookAction/showRemove";
    }

    @PostMapping("/search")
    private String searchArray(@RequestParam(value = "search") String searching_value, Model model){
        Page<Book> book_pages = bookService.find_searched_paginated(PageRequest.of(0,5),searching_value);
        model.addAttribute("book_pages",book_pages);

        if(book_pages.getTotalPages() > 0){
            List<Integer> page_num = IntStream.rangeClosed(1,book_pages.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers",page_num);
        }
        return "home";
    }

    @GetMapping("/showRemove")
    private String showRemoveForm(Model model){
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books",books);
        return "remove_book";
    }

    @GetMapping("/showAdd")
    private String showAddForm(Model model){
        model.addAttribute("book",new Book());
        return "add_book";
    }

    @GetMapping("/showAddBook")
    private String showAddBook(Model model){
        model.addAttribute("book", new Book());
        return "add_book_user";
    }

    @GetMapping("/bookDetail")
    private String getBookDetail(@RequestParam("id") Long id,Model model){
        Book book = bookRepository.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("id",id);
        return "book_detail";
    }
}
