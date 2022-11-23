package com.janwarlen.learn.mapstruct.entity.vo;

import com.janwarlen.learn.mapstruct.entity.DemoReqDTO;
import com.janwarlen.learn.mapstruct.entity.DemoResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DemoMapper {

    DemoMapper DEMO_MAPPER = Mappers.getMapper(DemoMapper.class);

    DemoReqDTO demoToDemoDTO(DemoReqVO req);

    DemoResVO demoToDemoResVO(DemoResDTO data);
}
