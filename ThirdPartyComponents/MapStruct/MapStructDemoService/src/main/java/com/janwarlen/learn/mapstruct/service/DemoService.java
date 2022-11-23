package com.janwarlen.learn.mapstruct.service;

import com.janwarlen.learn.mapstruct.entity.DemoReqDTO;
import com.janwarlen.learn.mapstruct.entity.DemoResDTO;

public interface DemoService {
    DemoResDTO demo(DemoReqDTO data);
}
