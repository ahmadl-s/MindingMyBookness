package com.mindingmybookness.Entity;

import com.mindingmybookness.Entity.Book;



public enum Month {

    JAN(new Book()), FEB(new Book()),
    MAR(new Book()), APR(new Book()),
    MAY(new Book()), JUN(new Book()),
    JUL(new Book()), AUG(new Book()),
    SEP(new Book()), OCT(new Book()),
    NOV(new Book()), DEC(new Book());

    private Book book;
     Month(Book book){
        this.book = book;
    }


    public void setBook(Book book) {
       this.book = book;
    }
}
