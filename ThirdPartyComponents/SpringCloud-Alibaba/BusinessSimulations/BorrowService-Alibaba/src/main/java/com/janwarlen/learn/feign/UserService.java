package com.janwarlen.learn.feign;

import com.janwarlen.learn.controller.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@FeignClient(value = "User-Service")
public interface UserService {
    @RequestMapping("/user/getUserByIds")
    public List<User> getUserByIds(@RequestBody Set<Integer> ids);
}
