package com.janwarlen.learn.controller;

import com.alibaba.fastjson.JSONObject;
import com.janwarlen.learn.entity.Book;
import com.janwarlen.learn.feign.LibraryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BorrowController {

    private HashMap<Integer, Set<Integer>> cache = new HashMap<>();

    @Resource
    RestTemplate restTemplate;

    @Resource
    LibraryService libraryService;

    @RequestMapping("/borrow/{uid}/{bid}")
    public String borrow(@PathVariable("uid") int uid, @PathVariable("bid") int bid) {
        Set<Integer> integers = cache.computeIfAbsent(uid, k -> new HashSet<>());
        integers.add(bid);
        return "success";
    }

    @RequestMapping("/borrow/borrowInfo/{uid}")
    public String borrowInfo(@PathVariable("uid") int uid) {
        Set<Integer> integers = cache.computeIfAbsent(uid, k -> new HashSet<>());
//        List<Book> forObject = restTemplate.postForObject("http://library-service/book/getBooks", integers, List.class);
        List<Book> books = libraryService.getBooks(new ArrayList<>(integers));
        return JSONObject.toJSONString(books);
    }
}
