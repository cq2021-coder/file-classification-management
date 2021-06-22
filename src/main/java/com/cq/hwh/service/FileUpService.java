package com.cq.hwh.service;

import com.cq.hwh.domain.FileUp;
import com.cq.hwh.domain.FileUpExample;
import com.cq.hwh.mapper.FileUpMapper;
import com.cq.hwh.req.FileUpQueryReq;
import com.cq.hwh.resp.FileUpQueryResp;
import com.cq.hwh.resp.PageResp;
import com.cq.hwh.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileUpService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private FileUpMapper fileUpMapper;

    public List<FileUpQueryResp> list(FileUpQueryReq req){
        FileUpExample fileUpExample = new FileUpExample();
        FileUpExample.Criteria criteria = fileUpExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getId())){
            criteria.andIdEqualTo(req.getId());
        }
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<FileUp> fileUpList = fileUpMapper.selectByExample(fileUpExample);
        return CopyUtil.copyList(fileUpList, FileUpQueryResp.class);
    }

    public PageResp<FileUpQueryResp> listpage(FileUpQueryReq req){
        FileUpExample fileUpExample = new FileUpExample();
        FileUpExample.Criteria criteria = fileUpExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getId())){
            criteria.andIdEqualTo(req.getId());
        }
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<FileUp> fileUpList = fileUpMapper.selectByExample(fileUpExample);

        PageInfo<FileUp> pageInfo = new PageInfo<>(fileUpList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        PageResp<FileUpQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(CopyUtil.copyList(fileUpList,FileUpQueryResp.class));

        return pageResp;
    }

}
