package com.janwarlen.learn.spock.demo.entity.VO;

import com.janwarlen.learn.spock.demo.entity.DTO.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserVoMapper {
    UserVoMapper USER_VO_MAPPER = Mappers.getMapper(UserVoMapper.class);

    UserVO convertToUserVO(UserDTO item);

    UserDTO convertToUserDTO(UserVO item);
}
