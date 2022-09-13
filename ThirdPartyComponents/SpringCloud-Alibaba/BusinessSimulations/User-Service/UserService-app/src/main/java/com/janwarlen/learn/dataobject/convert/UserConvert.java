package com.janwarlen.learn.dataobject.convert;

import com.janwarlen.learn.dataobject.UserDO;
import com.janwarlen.learn.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {
    UserConvert USER_CONVERT = Mappers.getMapper(UserConvert.class);

    User doToEntity(UserDO userDO);

    List<User> doToEntity(List<UserDO> userDO);
}
