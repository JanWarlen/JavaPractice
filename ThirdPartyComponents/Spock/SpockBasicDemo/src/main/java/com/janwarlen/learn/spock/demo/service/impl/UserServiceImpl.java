package com.janwarlen.learn.spock.demo.service.impl;

import com.janwarlen.learn.spock.demo.entity.DO.User;
import com.janwarlen.learn.spock.demo.entity.DTO.UserDTO;
import com.janwarlen.learn.spock.demo.entity.DTO.UserDtoMapper;
import com.janwarlen.learn.spock.demo.mapper.UserMapper;
import com.janwarlen.learn.spock.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return UserDtoMapper.USER_DTO_MAPPER.converToUserDTO(user);
    }
}
