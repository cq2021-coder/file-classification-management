package com.cq.hwh.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @ApiOperation("这是一个POST测试接口")
    @PostMapping("/helloPost")
    public String helloPost(){
        return "你好呀Post";
    }
    @ApiOperation("这是一个GET测试接口")
    @GetMapping("/helloGet")
    public String helloGet(){
        return "你好呀Get";
    }
    @ApiOperation("这是一个DELETE测试接口")
    @DeleteMapping("/helloDelete")
    public String helloDelete(){
        return "你好呀Delete";
    }
}
