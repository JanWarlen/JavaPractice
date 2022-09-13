package com.janwarlen.learn.service.impl;

import com.janwarlen.learn.dataobject.UserDO;
import com.janwarlen.learn.dataobject.convert.UserConvert;
import com.janwarlen.learn.entity.User;
import com.janwarlen.learn.mapper.UserMapper;
import com.janwarlen.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserById(int id) {
        UserDO userById = userMapper.getUserById(id);
        return UserConvert.USER_CONVERT.doToEntity(userById);
    }

    @Override
    public List<User> getUserByIds(Set<Integer> ids) {
        List<UserDO> userByIds = userMapper.getUserByIds(ids);
        return UserConvert.USER_CONVERT.doToEntity(userByIds);
    }
}
