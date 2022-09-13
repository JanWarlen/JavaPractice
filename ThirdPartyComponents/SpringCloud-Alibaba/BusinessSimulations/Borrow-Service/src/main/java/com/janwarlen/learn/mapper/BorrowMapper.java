package com.janwarlen.learn.mapper;

import com.janwarlen.learn.dataobject.BorrowRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BorrowMapper {
    BorrowRecordDO checkBookIsBorrowedByUid(@Param("uid")int uid,@Param("bid")int bid);

    int borrowBookByUid(@Param("uid")int uid,@Param("bid")int bid);

    int returnBookByUid(@Param("uid")int uid,@Param("bid")int bid);
}
