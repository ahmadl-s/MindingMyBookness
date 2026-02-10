package com.mindingmybookness;

import org.hibernate.grammars.hql.HqlParser;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.print.attribute.standard.PresentationDirection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class Contoller {

    private Service service;

    @GetMapping
    public List<Book> showAllBooks(){
        return service.getAllBooks();
    }

    @PostMapping("/booknamesearch")
    public List<Book> showBookByName(String name){
        return service.getBookByName(name);
    }

    @PostMapping("/Authorbookssearch")
    public List<Book> showBookByAuthor(String Author){
        return service.getBookByAuthor(Author);
    }

    @PostMapping("/Addbook")
    public void addbook(Book book){
        service.addbook(book);
    }




}
