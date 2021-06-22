package com.cq.hwh.service;

import com.cq.hwh.domain.Category;
import com.cq.hwh.domain.CategoryExample;
import com.cq.hwh.mapper.CategoryMapper;
import com.cq.hwh.req.CategoryQueryReq;
import com.cq.hwh.resp.CategoryQueryResp;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    public List<CategoryQueryResp> list(CategoryQueryReq req){
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getId())){
            criteria.andIdEqualTo(req.getId());
        }
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        return CopyUtil.copyList(categoryList, CategoryQueryResp.class);
    }
    public PageResp<CategoryQueryResp> pagelist(CategoryQueryReq req){
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getId())){
            criteria.andIdEqualTo(req.getId());
        }
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(CopyUtil.copyList(categoryList, CategoryQueryResp.class));

        return pageResp;
    }

}
