package com.mindingmybookness.DTOs;

import com.mindingmybookness.Entity.Month;
import lombok.Data;


@Data

public class BookRequest {
    private String bookname;

    private String author;

    private String description;

    private Month month;
}
