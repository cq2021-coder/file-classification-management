package com.cq.hwh.controller;

import com.cq.hwh.req.FileUpEditReq;
import com.cq.hwh.req.FileUpQueryReq;
import com.cq.hwh.req.FileUpSaveReq;
import com.cq.hwh.resp.CommonResp;
import com.cq.hwh.resp.FileUpQueryResp;
import com.cq.hwh.resp.PageResp;
import com.cq.hwh.service.FileUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Api(tags = "文件信息操作")
@RestController
@RequestMapping("/fileup")
public class FileUpController {
    @Resource
    private FileUpService fileUpService;

    @ApiOperation("信息查询接口  可传入分页参数进行分页查询 id和name为空查询所有信息，id有值查询单个信息,name可进行模糊查询,注意两个参数要对应")
    @GetMapping("/list")
    public CommonResp list(@Valid FileUpQueryReq req){
        CommonResp<PageResp<FileUpQueryResp>> resp =new CommonResp<>();
        PageResp<FileUpQueryResp> list = fileUpService.list(req);
        resp.setContent(list);
        return resp;
    }

    @ApiOperation("文件上传接口")
    @PostMapping("/save")
    public CommonResp save(@RequestParam(value = "file", required = false) MultipartFile file, FileUpSaveReq req){
        CommonResp resp = new CommonResp();
        fileUpService.save(file,req);
        return resp;
    }


    @ApiOperation("文件编辑接口   可修改文件名称和文件分类（注意文件分类要存在，不然会报系统异常）")
    @PostMapping("/edit")
    public CommonResp edit(@RequestBody FileUpEditReq req){
        CommonResp resp = new CommonResp();
        fileUpService.edit(req);
        return resp;
    }



    /**
     * 删除
     * @param idsStr
     * @return
     */
    @ApiOperation("删除接口  传入单个或多个id进行删除操作，多个id示例：1,2,3(英文版逗号),注意我后端生成的id即使是文件一样，名称一样，id仍然不同")
    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr){
        CommonResp resp = new CommonResp();
        List<String> list = Arrays.asList(idsStr.split(","));
        fileUpService.delete(list);
        return resp;
    }
}
