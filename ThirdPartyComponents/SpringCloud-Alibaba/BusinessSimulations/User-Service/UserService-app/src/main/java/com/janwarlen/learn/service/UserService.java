package com.janwarlen.learn.service;

import com.janwarlen.learn.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User getUserById(int id);

    List<User> getUserByIds(Set<Integer> ids);
}
