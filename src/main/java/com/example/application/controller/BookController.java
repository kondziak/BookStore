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
import java.util.List;

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

}
