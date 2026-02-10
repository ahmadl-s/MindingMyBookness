package com.mindingmybookness.Controller;

import com.mindingmybookness.Entity.Book;
import com.mindingmybookness.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Book> showBookByNameController(@RequestParam String bookname){
        return bookService.getBookByName(bookname);
    }

    @GetMapping("/authorbookssearch")
    public List<Book> showBookByAuthorController(@RequestParam String author){
        return bookService.getBookByAuthor(author);
    }

    @PostMapping("/addbook")
    public ResponseEntity<String> addBookController(@RequestBody Book book){
       return bookService.addBook(book);
    }

    @DeleteMapping("/deletebook")
    public ResponseEntity<String> deleteBookController( @RequestParam String name){
       return bookService.deleteBook(name);
    }

    @PutMapping ("/editbook/{id}")
    public ResponseEntity<String> editBookController(
            @PathVariable Integer id,
            @RequestBody Book book

            ){

        return bookService.editBook(id, book);
    }




}
