package com.cq.hwh.controller;

import com.cq.hwh.req.CategoryQueryReq;
import com.cq.hwh.req.CategorySaveReq;
import com.cq.hwh.resp.CategoryQueryResp;
import com.cq.hwh.resp.CommonResp;
import com.cq.hwh.resp.PageResp;
import com.cq.hwh.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Api(tags = "分类信息操作")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @ApiOperation("信息分类查询接口  可传入分页参数;id和name为空查询所有信息，id有值查询单个信息,name可进行模糊查询,注意两个参数要对应")
    @PostMapping("/list")
    public CommonResp list(@RequestBody CategoryQueryReq req){
        CommonResp<PageResp<CategoryQueryResp>> resp =new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @ApiOperation("编辑或新增接口  传入id时为编辑，不传入id时为新增")
    @PostMapping("/save")
    public CommonResp save(@RequestBody CategorySaveReq req){
        CommonResp resp = new CommonResp();
        categoryService.save(req);
        return resp;
    }

    @ApiOperation("删除接口  传入单个或多个id进行删除操作，多个id示例：1,2,3(英文版逗号)")
    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr){
        CommonResp resp = new CommonResp();
        List<String> list = Arrays.asList(idsStr.split(","));
        categoryService.delete(list);
        return resp;
    }
}
