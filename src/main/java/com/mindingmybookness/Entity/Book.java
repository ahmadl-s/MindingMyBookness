package com.mindingmybookness.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.service.invoker.UrlArgumentResolver;

import java.net.URL;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Book {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;


    private String bookname;

    private String author;

    private String description;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Month month;

}
