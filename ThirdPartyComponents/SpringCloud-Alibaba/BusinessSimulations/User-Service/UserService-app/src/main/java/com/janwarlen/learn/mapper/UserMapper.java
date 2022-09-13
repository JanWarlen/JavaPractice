package com.janwarlen.learn.mapper;

import com.janwarlen.learn.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    UserDO getUserById(@Param("id") int id);

    List<UserDO> getUserByIds(@Param("ids") Set<Integer> ids);
}
