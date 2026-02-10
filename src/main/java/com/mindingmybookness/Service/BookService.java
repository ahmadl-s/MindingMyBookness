package com.mindingmybookness.Service;


import com.mindingmybookness.Repository.BookRepository;
import com.mindingmybookness.Entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) { //you're tellign spring that: To create a service, you must give me a bookrepository
        this.bookRepository = bookRepository;
    }

    //Get All books
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }


    //Get Books My name
    public List<Book> getBookByName(String bookname){

        return bookRepository.findBookByBooknameContains(bookname);
    }
    //Get books by author
    public List<Book> getBookByAuthor(String authorname){
        return bookRepository.findBookByAuthorContains(authorname);
    }

    //add a book
    public ResponseEntity<String> addBook(Book book) {

        Book sameMonthBook = bookRepository.findBookByMonthIs(book.getMonth());

        if (sameMonthBook != null) {
            return new ResponseEntity<>("A book has occupied chosen month", HttpStatus.CONFLICT);
        } else {
            bookRepository.save(book);
            return new ResponseEntity<>("Book saved", HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<String> deleteBook(String name){
        bookRepository.deleteBookByBookname(name);
        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> editBook( Integer id, Book updateBook) {
        Book bookToEdit = bookRepository.findBookById(id);

        if (bookToEdit != null) {

            bookToEdit.setId(bookToEdit.getId());
            bookToEdit.setBookname(updateBook.getBookname());
            bookToEdit.setDescription(updateBook.getDescription());
            bookToEdit.setAuthor(updateBook.getAuthor());

            bookRepository.save(bookToEdit);

            return ResponseEntity.ok("Book has been updated");
        } else {
            return ResponseEntity.ok("Book dosent exists");
        }
    }

}



