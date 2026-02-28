package com.mindingmybookness.Service;


import com.mindingmybookness.DTOs.BookRequest;
import com.mindingmybookness.DTOs.EditBookRequest;
import com.mindingmybookness.Entity.Month;
import com.mindingmybookness.Repository.BookRepository;
import com.mindingmybookness.Entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ResponseEntity<String> addBook(BookRequest bookRequest) {

        Book sameMonthBook = bookRepository.findBookByMonthIs(bookRequest.getMonth());

        if (sameMonthBook != null) {
            return new ResponseEntity<>("A book has occupied chosen month", HttpStatus.CONFLICT);
        } else {
            Book book = Book.builder()
                    .bookname(bookRequest.getBookname())
                    .author(bookRequest.getAuthor())
                    .description(bookRequest.getDescription())
                    .month(bookRequest.getMonth())
                    .build();
            bookRepository.save(book);
            return new ResponseEntity<>("Book saved", HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<String> deleteBook(String name){
        bookRepository.deleteBookByBookname(name);
        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }


    @Transactional // Hibernate will now "watch" for changes
    public ResponseEntity<String> editBook(int id, EditBookRequest editBookRequest) {
        Book bookToEdit = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Just update the fields. No .save() needed!
        bookToEdit.setBookname(editBookRequest.getBookname());
        bookToEdit.setDescription(editBookRequest.getDescription());
        bookToEdit.setAuthor(editBookRequest.getAuthor());


        return ResponseEntity.ok("Book has been updated");
    } // At this bracket, the database is automatically updated bcos of @Transactonal


    @Transactional
    public ResponseEntity<String> editBookMonth(int id, Month newMonth){

        Book bookIdExist = bookRepository.findBookById(id);
        Book bookMonthExist = bookRepository.findBookByMonthIs(newMonth);

        if(bookIdExist != null && bookMonthExist ==null ){
            bookIdExist.setMonth(newMonth);
            return ResponseEntity.ok("Book month has been updated");
        }

        return new ResponseEntity<>("book id doesnt exist or month already taken", HttpStatus.BAD_REQUEST);
    }

}



