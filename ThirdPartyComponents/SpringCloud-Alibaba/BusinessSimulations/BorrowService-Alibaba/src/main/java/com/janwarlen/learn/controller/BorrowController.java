package com.janwarlen.learn.controller;

import com.janwarlen.learn.feign.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
public class BorrowController {

    private HashMap<Integer, Set<Integer>> cache = new HashMap<>();

    @Resource
    UserService userService;

    @RequestMapping("/borrow/{uid}/{bid}")
    public String borrow(@PathVariable("uid") int uid, @PathVariable("bid") int bid) {
        Set<Integer> integers = cache.computeIfAbsent(uid, k -> new HashSet<>());
        integers.add(bid);
        return "success";
    }

    @RequestMapping("/borrow/getMember")
    public List<User> getMember() {
        Set<Integer> integers = cache.keySet();
        if (integers.isEmpty()) {
            return Collections.emptyList();
        }
        return userService.getUserByIds(integers);
    }
}
