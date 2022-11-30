package com.janwarlen.learn.spock.demo.entity.DTO;

import com.janwarlen.learn.spock.demo.entity.DO.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoMapper {
    UserDtoMapper USER_DTO_MAPPER = Mappers.getMapper(UserDtoMapper.class);

    User converToUser(UserDTO item);

    UserDTO converToUserDTO(User item);
}
