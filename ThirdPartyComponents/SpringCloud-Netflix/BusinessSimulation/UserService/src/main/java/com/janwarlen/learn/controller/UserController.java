package com.janwarlen.learn.controller;

import com.janwarlen.learn.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/user/{id}")
    public User getUser(@PathVariable("id") int id) {
        User user = new User();
        user.setId(id);
        user.setName("test");
        return user;
    }
}
