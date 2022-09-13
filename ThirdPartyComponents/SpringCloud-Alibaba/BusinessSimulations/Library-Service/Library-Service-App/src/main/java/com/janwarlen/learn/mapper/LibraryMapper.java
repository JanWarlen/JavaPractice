package com.janwarlen.learn.mapper;

import com.janwarlen.learn.dataobject.BookDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LibraryMapper {

    BookDO getBookById(@Param("id")int id);

    int updateBookById(@Param("book") BookDO bookDO);
}
