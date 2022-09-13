package com.janwarlen.learn.service.impl;

import com.janwarlen.learn.dataobject.BorrowRecordDO;
import com.janwarlen.learn.mapper.BorrowMapper;
import com.janwarlen.learn.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    BorrowMapper borrowMapper;

    @Override
    public BorrowRecordDO checkBookIsBorrowedByUid(int uid, int bid) {
        return borrowMapper.checkBookIsBorrowedByUid(uid, bid);
    }

    @Override
    public int borrowBookByUid(int uid, int bid) {
        return borrowMapper.borrowBookByUid(uid, bid);
    }

    @Override
    public int returnBookByUid(int uid, int bid) {
        return borrowMapper.returnBookByUid(uid, bid);
    }
}
