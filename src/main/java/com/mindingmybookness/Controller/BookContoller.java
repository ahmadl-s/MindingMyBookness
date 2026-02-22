package com.mindingmybookness.Controller;

import com.mindingmybookness.DTOs.BookRequest;
import com.mindingmybookness.Entity.Book;
import com.mindingmybookness.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableMethodSecurity
@RestController
@RequestMapping("/books")
public class BookContoller {

    private final BookService bookService;


    @Autowired
    public BookContoller(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> showAllBooksController(){
        return bookService.getAllBooks();
    }

    @GetMapping("/booknamesearch")
    public List<Book> showBookByNameController(@RequestParam String name){
        return bookService.getBookByName(name);
    }

    @GetMapping("/authorbookssearch")
    public List<Book> showBookByAuthorController(@RequestParam String author){
        return bookService.getBookByAuthor(author);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addbook")
    public ResponseEntity<String> addBookController(@RequestBody BookRequest addBookRequest){
       return bookService.addBook(addBookRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletebook")
    public ResponseEntity<String> deleteBookController( @RequestParam String name){
       return bookService.deleteBook(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/editbook/{id}")
    public ResponseEntity<String> editBookController(
            @PathVariable Integer id,
            @RequestBody BookRequest bookRequest

            ){

        return bookService.editBook(id, bookRequest);
    }




}
