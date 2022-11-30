package com.janwarlen.learn.spock.demo.controller;

import com.janwarlen.learn.spock.demo.entity.VO.UserVO;
import com.janwarlen.learn.spock.demo.entity.VO.UserVoMapper;
import com.janwarlen.learn.spock.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserById")
    public UserVO getUserById(@RequestParam Integer id) {
        return UserVoMapper.USER_VO_MAPPER.convertToUserVO(userService.getUserById(id));
    }
}
