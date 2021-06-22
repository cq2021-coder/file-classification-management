package com.cq.hwh.service;

import com.cq.hwh.domain.Category;
import com.cq.hwh.domain.CategoryExample;
import com.cq.hwh.exception.BusinessException;
import com.cq.hwh.exception.BusinessExceptionCode;
import com.cq.hwh.mapper.CategoryMapper;
import com.cq.hwh.req.CategoryQueryReq;
import com.cq.hwh.req.CategorySaveReq;
import com.cq.hwh.resp.CategoryQueryResp;
import com.cq.hwh.resp.PageResp;
import com.cq.hwh.util.CopyUtil;
import com.cq.hwh.util.SnowFlake;
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

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req){
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

    /**
     * 保存或编辑
     * @param req
     */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req,Category.class);
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        criteria.andNameEqualTo(req.getName());

        if (ObjectUtils.isEmpty(req.getId())){
            if (ObjectUtils.isEmpty(categoryMapper.selectByExample(categoryExample))) {
                category.setId(snowFlake.nextId() / 10000);
                categoryMapper.insert(category);
            }
            else {
                throw new BusinessException(BusinessExceptionCode.CATEGORY_ERROR);
            }
        }
        else {
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(List<String> ids){
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andIdIn(ids);
        categoryMapper.deleteByExample(categoryExample);
    }

}
