package com.mindingmybookness;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.OptionalLong;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBookByBookname(String bookname);

    List<Book> findBookByAuthor(String name);
}
