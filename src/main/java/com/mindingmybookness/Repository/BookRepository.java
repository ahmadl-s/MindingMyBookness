package com.mindingmybookness.Repository;

import com.mindingmybookness.Entity.Book;
import com.mindingmybookness.Entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBookByBooknameContains(String bookname);

    List<Book> findBookByAuthorContains(String author);

    void deleteBookByBookname(String bookname);


    Book findBookById(Integer id);

    Book findBookByBookname(String bookname);

    Book findBookByMonthIs(Month month);
}
