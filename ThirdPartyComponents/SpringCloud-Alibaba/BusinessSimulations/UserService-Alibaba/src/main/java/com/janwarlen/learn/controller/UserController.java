package com.janwarlen.learn.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @RequestMapping("/user/{id}")
    public User getUser(@PathVariable("id") int id) {
        User user = new User();
        user.setId(id);
        user.setName("test");
        return user;
    }

    @RequestMapping("/user/getUserByIds")
    @SentinelResource(value = "getUserByIds", blockHandler = "defaultUsers")
    public List<User> getUserByIds(@RequestBody Set<Integer> ids) {
        List<User> res = new ArrayList<>();
        for (Integer id : ids) {
            User user = new User();
            user.setId(id);
            user.setName("test");
            res.add(user);
        }
        return res;
    }

    public List<User> defaultUsers(Set<Integer> ids, BlockException e) {
        List<User> res = new ArrayList<>();
        User user = new User();
        user.setName("sentinel flow controller");
        res.add(user);
        return res;
    }

    @RequestMapping("/blocked")
    JSONObject blocked(){
        JSONObject object = new JSONObject();
        object.put("code", 403);
        object.put("success", false);
        object.put("massage", "您的请求频率过快，请稍后再试！");
        return object;
    }
}
