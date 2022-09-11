package com.janwarlen.learn.controller;

import com.janwarlen.learn.entity.Book;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class LibraryController {

    @RequestMapping("/book/{id}")
    public Book getBook(@PathVariable("id") int id, HttpServletRequest request) {
        System.out.println(request.getHeader("Test"));
        Book book = new Book();
        book.setId(id);
        book.setName("testBook");
        return book;
    }

    @HystrixCommand(fallbackMethod = "onTimeout",
            // 规定 100 ms以内就不报错，正常运行，超过 100 ms就报错，调用指定的方法
            commandProperties={@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")})
    @RequestMapping("/book/getBooks")
    public List<Book> getBooks(@RequestBody List<Integer> bids) {
        ArrayList<Book> books = new ArrayList<>();
        if (CollectionUtils.isEmpty(bids)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return books;
        }
        for (Integer bid : bids) {
            Book book = new Book();
            book.setId(bid);
            book.setName("testBook");
            books.add(book);
        }
        return books;
    }

    List<Book> onTimeout(List<Integer> bids) {
        System.out.println("服务异常，快速失败！");
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("onTimeout"));
        return books;
    }
}
