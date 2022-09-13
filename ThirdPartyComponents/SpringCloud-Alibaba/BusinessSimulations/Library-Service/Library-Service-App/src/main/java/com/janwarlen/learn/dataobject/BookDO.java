package com.janwarlen.learn.dataobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BookDO {
    private int id;
    private String name;
    private int inventory;
}
