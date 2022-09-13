package com.janwarlen.learn.service.impl;

import com.janwarlen.learn.dataobject.BookDO;
import com.janwarlen.learn.dataobject.convert.BookConvert;
import com.janwarlen.learn.entity.Book;
import com.janwarlen.learn.mapper.LibraryMapper;
import com.janwarlen.learn.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    LibraryMapper libraryMapper;

    @Override
    public Book getBookById(int id) {
        BookDO bookById = libraryMapper.getBookById(id);
        return BookConvert.BOOK_CONVERT.doToEntity(bookById);
    }

    @Override
    public int updateBookById(Book book) {
        BookDO bookDO = BookConvert.BOOK_CONVERT.entityToDO(book);
        return libraryMapper.updateBookById(bookDO);
    }
}
