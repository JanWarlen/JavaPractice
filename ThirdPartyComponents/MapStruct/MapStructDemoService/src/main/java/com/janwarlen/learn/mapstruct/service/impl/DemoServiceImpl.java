package com.janwarlen.learn.mapstruct.service.impl;

import com.janwarlen.learn.mapstruct.entity.DemoReqDTO;
import com.janwarlen.learn.mapstruct.entity.DemoResDTO;
import com.janwarlen.learn.mapstruct.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public DemoResDTO demo(DemoReqDTO data) {
        System.out.println(data.getName());
        DemoResDTO res = new DemoResDTO();
        res.setName("hi:" + data.getName());
        return res;
    }
}
