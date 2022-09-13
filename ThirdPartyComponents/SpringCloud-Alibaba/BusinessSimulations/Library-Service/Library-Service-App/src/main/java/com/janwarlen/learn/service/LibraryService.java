package com.janwarlen.learn.service;

import com.janwarlen.learn.entity.Book;

public interface LibraryService {

    Book getBookById(int id);

    int updateBookById(Book book);

}
