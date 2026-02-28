package com.mindingmybookness.DTOs;

import lombok.Data;

@Data
public class EditBookRequest {

    private String bookname;

    private String author;

    private String description;

}
