package com.janwarlen.learn.feign;

import com.janwarlen.learn.entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "Library-Service", fallback = LibraryFallbackService.class)
public interface LibraryService {
    @RequestMapping("/book/getBooks")
    public List<Book> getBooks(@RequestBody List<Integer> bids);
}
