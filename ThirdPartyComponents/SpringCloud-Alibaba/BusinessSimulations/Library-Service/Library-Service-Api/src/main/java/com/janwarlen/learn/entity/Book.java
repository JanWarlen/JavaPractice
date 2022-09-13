package com.janwarlen.learn.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Book {
    private int id;
    private String name;
    private int inventory;
}
