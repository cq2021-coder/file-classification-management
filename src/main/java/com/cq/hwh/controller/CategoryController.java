package com.cq.hwh.controller;

import com.cq.hwh.req.CategoryQueryReq;
import com.cq.hwh.resp.CategoryQueryResp;
import com.cq.hwh.resp.CommonResp;
import com.cq.hwh.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "分类信息操作")
@RestController
@RequestMapping("category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @ApiOperation("id和name为空查询所有信息，id有值查询单个信息,name可进行模糊查询,注意两个参数要对应")
    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req){
        CommonResp<List<CategoryQueryResp>> resp =new CommonResp<>();
        List<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }
}
