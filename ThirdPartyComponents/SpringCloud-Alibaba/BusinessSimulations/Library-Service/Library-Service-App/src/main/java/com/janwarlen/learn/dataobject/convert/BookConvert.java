package com.janwarlen.learn.dataobject.convert;

import com.janwarlen.learn.dataobject.BookDO;
import com.janwarlen.learn.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookConvert {
    BookConvert BOOK_CONVERT = Mappers.getMapper(BookConvert.class);

    Book doToEntity(BookDO bookDO);

    List<Book> doToEntity(List<BookDO> bookDO);

    BookDO entityToDO(Book book);
}
