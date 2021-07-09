package com.cq.hwh.controller;

import com.cq.hwh.annotation.PassToken;
import com.cq.hwh.req.UserLoginReq;
import com.cq.hwh.req.UserQueryReq;
import com.cq.hwh.req.UserResetPasswordReq;
import com.cq.hwh.req.UserSaveReq;
import com.cq.hwh.resp.CommonResp;
import com.cq.hwh.resp.PageResp;
import com.cq.hwh.resp.UserLoginResp;
import com.cq.hwh.resp.UserQueryResp;
import com.cq.hwh.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("根据账号查询用户  不传入账号为查询全部用户")
    @GetMapping("/list")
    public CommonResp list(@RequestBody UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    @ApiOperation("保存或编辑")
    @PostMapping("/save")
    public CommonResp save(@RequestBody UserSaveReq req) {
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @ApiOperation("重置密码,输入要更改的id以及更改的密码")
    @PostMapping("/reset-password")
    public CommonResp resetPassword(@RequestBody UserResetPasswordReq req) {
        CommonResp resp = new CommonResp<>();
        userService.resetPassword(req);
        return resp;
    }

    @PassToken
    @ApiOperation("登录")
    @PostMapping("/login")
    public CommonResp login(@RequestBody UserLoginReq req) {
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp = userService.login(req);
        resp.setContent(userLoginResp);
        return resp;
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

    @ApiOperation("查询所有用户")
    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<UserQueryResp>> resp = new CommonResp<>();
        List<UserQueryResp> all = userService.all();
        resp.setContent(all);
        return resp;
    }
}
