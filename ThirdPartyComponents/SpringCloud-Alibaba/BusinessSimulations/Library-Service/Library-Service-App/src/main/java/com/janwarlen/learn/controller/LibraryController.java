package com.janwarlen.learn.controller;

import com.janwarlen.learn.entity.Book;
import com.janwarlen.learn.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @RequestMapping("/library/{id}")
    public Book getBookById(@PathVariable("id") int id) {
        return libraryService.getBookById(id);
    }

    @RequestMapping("/library/updateBookById")
    public int updateBookById(@RequestBody Book book) {
        throw new RuntimeException("test");
//        return libraryService.updateBookById(book);
    }
}
