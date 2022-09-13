package com.janwarlen.learn.dataobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BorrowRecordDO {
    private int id;
    private int uid;
    private int bid;
    private int release;
    private Date gmtCreate;
    private Date gmtModify;
}
