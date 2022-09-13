package com.janwarlen.learn.feign;

import com.janwarlen.learn.entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "Library-Service")
public interface LibraryService {
    @RequestMapping("/library/{id}")
    Book getBookById(@PathVariable("id") int id);

    @RequestMapping("/library/updateBookById")
    int updateBookById(@RequestBody Book book);
}
