package com.janwarlen.learn.spock.demo.service;

import com.janwarlen.learn.spock.demo.entity.DTO.UserDTO;

public interface UserService {

    UserDTO getUserById(Integer id);
}
