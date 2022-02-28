package com.example.application.controller;

import com.example.application.model.Book;
import com.example.application.repository.BookRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/book_action")
public class BookController {

    private final BookRepository book_repository;

    public BookController(@NonNull @Lazy BookRepository book_repository) {
        super();
        this.book_repository = book_repository;
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
        return "redirect:/add_book";
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
        Set<Book> allBooks = new HashSet<>();
        List<Book> books = book_repository.findByTitle(searching_value);
        if(books != null){
            allBooks.addAll(books);
        }
        books = book_repository.findByAuthor(searching_value);
        if(books != null){
            allBooks.addAll(books);
        }
        books = book_repository.findByCategory(searching_value);
        if(books != null){
            allBooks.addAll(books);
        }
        if(allBooks.size() > 0){
            model.addAttribute("books",allBooks.stream().toList());
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
        return "book_detail";
    }
}
