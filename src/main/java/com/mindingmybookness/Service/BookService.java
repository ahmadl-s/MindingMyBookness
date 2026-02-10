package com.mindingmybookness;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private final BookRepository bookRepository;

    @Autowired
    public Service(BookRepository bookRepository) { //you're tellign spring that: To create a service, you must give me a bookrepository
        this.bookRepository = bookRepository;
    }

    //Get All books
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }


    //Get Books My name
    public List<Book> getBookByName(String bookname){
        return bookRepository.findBookByBookname(bookname);
    }
    //Get books by author
    public List<Book> getBookByAuthor(String authorname){
        return bookRepository.findBookByAuthor(authorname);
    }

    //add a book
    public void addbook(Book book){
        bookRepository.save(book);
    }




}
