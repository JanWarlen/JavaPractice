package com.janwarlen.learn.service;

import com.janwarlen.learn.dataobject.BorrowRecordDO;

public interface BorrowService {
    BorrowRecordDO checkBookIsBorrowedByUid(int uid,int bid);

    int borrowBookByUid(int uid,int bid);

    int returnBookByUid(int uid,int bid);
}
