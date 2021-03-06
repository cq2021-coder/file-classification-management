package com.cq.hwh.service;

import com.cq.hwh.domain.Category;
import com.cq.hwh.domain.CategoryExample;
import com.cq.hwh.domain.FileUp;
import com.cq.hwh.domain.FileUpExample;
import com.cq.hwh.exception.BusinessException;
import com.cq.hwh.exception.BusinessExceptionCode;
import com.cq.hwh.mapper.CategoryMapper;
import com.cq.hwh.mapper.FileUpMapper;
import com.cq.hwh.req.FileUpEditReq;
import com.cq.hwh.req.FileUpQueryReq;
import com.cq.hwh.req.FileUpSaveReq;
import com.cq.hwh.resp.FileUpQueryResp;
import com.cq.hwh.resp.PageResp;
import com.cq.hwh.util.CopyUtil;
import com.cq.hwh.util.FileUtil;
import com.cq.hwh.util.PathUtil;
import com.cq.hwh.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class FileUpService {

    private static final Logger LOG = LoggerFactory.getLogger(FileUpService.class);

    @Resource
    private FileUpMapper fileUpMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private CategoryMapper categoryMapper;

    public PageResp<FileUpQueryResp> list(FileUpQueryReq req){
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
        LOG.info("????????????{}",pageInfo.getTotal());
        LOG.info("????????????{}",pageInfo.getPages());

        PageResp<FileUpQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(CopyUtil.copyList(fileUpList,FileUpQueryResp.class));

        return pageResp;
    }

    /**
     * ??????????????????????????????
     * @param name
     * @return
     */
    public FileUp queryByName(String name){
        FileUpExample fileUpExample = new FileUpExample();
        FileUpExample.Criteria criteria = fileUpExample.createCriteria();
        criteria.andNameEqualTo(name);
        return fileUpMapper.selectByExample(fileUpExample).get(0);
    }

    /**
     * ??????
     * @param file
     * @param req
     */

    @Transactional
    public void save(MultipartFile file, FileUpSaveReq req ){
        FileUpExample fileUpExample = new FileUpExample();
        FileUpExample.Criteria criteria = fileUpExample.createCriteria();
        criteria.andNameEqualTo(req.getName());

        if (!ObjectUtils.isEmpty(fileUpMapper.selectByExample(fileUpExample))){
            throw new BusinessException(BusinessExceptionCode.FILE_MORE_ERROR);
        }

        FileUp fileUp = CopyUtil.copy(req, FileUp.class);

        //ID
        fileUp.setId(snowFlake.nextId()/10000);

        //??????
        fileUp.setCreateTime(new Date());

         //??????id
         CategoryExample categoryExample = new CategoryExample();
         CategoryExample.Criteria criteria01 = categoryExample.createCriteria();
         criteria01.andNameEqualTo(req.getCategoryName());
         List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
         fileUp.setCategoryId(categoryList.get(0).getId());

        //????????????
        if (!ObjectUtils.isEmpty(categoryList)) {
            String filePath = FileUtil.upFile(file, fileUp.getName(), req.getCategoryName());
            fileUp.setFilePath(filePath);
        }

         fileUpMapper.insert(fileUp);
    }

    public void edit(FileUpEditReq req){
        FileUp fileUp = CopyUtil.copy(req, FileUp.class);
        fileUp.setFilePath(fileUpMapper.selectByPrimaryKey(req.getId()).getFilePath());
        fileUp.setCreateTime(new Date());

        //??????id
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andNameEqualTo(req.getCategoryName());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

         if (categoryList.size()!=0){
             fileUp.setCategoryId(categoryList.get(0).getId());
             fileUpMapper.updateByPrimaryKey(fileUp);
        }else {
            throw new IndexOutOfBoundsException("???????????????,????????????????????????");
        }

    }


    /**
     * ??????
     * @param ids
     */
    public void delete(List<String> ids){
        FileUpExample fileUpExample = new FileUpExample();
        FileUpExample.Criteria criteria = fileUpExample.createCriteria();
        criteria.andIdIn(ids);

        String basePath = PathUtil.getResultPath();

        List<FileUp> fileUps = fileUpMapper.selectByExample(fileUpExample);
        if (!ObjectUtils.isEmpty(fileUps)) {
            for (FileUp fileUp : fileUps) {
                File file = new File(basePath + fileUp.getFilePath());
                if (file.delete()) {
                    fileUpMapper.deleteByPrimaryKey(fileUp.getId());
                } else {
                    throw new BusinessException(BusinessExceptionCode.FILE_DELETE_ERROR);
                }
            }
        }else {
            throw new BusinessException(BusinessExceptionCode.FILE_NULL_ERROR);
        }
    }

}
