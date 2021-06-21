package com.cq.hwh.service;

import com.cq.hwh.domain.Category;
import com.cq.hwh.domain.CategoryExample;
import com.cq.hwh.mapper.CategoryMapper;
import com.cq.hwh.req.CategoryQueryReq;
import com.cq.hwh.resp.CategoryQueryResp;
import com.cq.hwh.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

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

}
