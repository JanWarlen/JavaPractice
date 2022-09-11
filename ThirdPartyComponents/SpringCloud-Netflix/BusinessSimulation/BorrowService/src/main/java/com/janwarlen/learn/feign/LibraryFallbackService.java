package com.janwarlen.learn.feign;

import com.janwarlen.learn.entity.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryFallbackService implements LibraryService {

    @Override
    public List<Book> getBooks(List<Integer> bids) {
        System.out.println("触发feign快速失败!(熔断)");
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("feign fall back"));
        return books;
    }
}
