package com.janwarlen.learn.mapstruct.controller;

import com.janwarlen.learn.mapstruct.entity.DemoResDTO;
import com.janwarlen.learn.mapstruct.entity.vo.DemoMapper;
import com.janwarlen.learn.mapstruct.entity.vo.DemoReqVO;
import com.janwarlen.learn.mapstruct.entity.vo.DemoResVO;
import com.janwarlen.learn.mapstruct.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/demo")
    public DemoResVO demo(@RequestBody DemoReqVO req) {
        DemoResDTO demo = demoService.demo(DemoMapper.DEMO_MAPPER.demoToDemoDTO(req));
        return DemoMapper.DEMO_MAPPER.demoToDemoResVO(demo);
    }
}
