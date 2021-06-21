package com.cq.hwh.controller;

import com.cq.hwh.req.FileUpQueryReq;
import com.cq.hwh.resp.FileUpQueryResp;
import com.cq.hwh.resp.CommonResp;
import com.cq.hwh.service.FileUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "文件信息操作")
@RestController
@RequestMapping("/fileup")
public class FileUpController {
    @Resource
    private FileUpService fileUpService;

    @ApiOperation("信息查询接口  id和name为空查询所有信息，id有值查询单个信息,name可进行模糊查询,注意两个参数要对应")
    @GetMapping("/list")
    public CommonResp list(@Valid FileUpQueryReq req){
        CommonResp<List<FileUpQueryResp>> resp =new CommonResp<>();
        List<FileUpQueryResp> list = fileUpService.list(req);
        resp.setContent(list);
        return resp;
    }
}
