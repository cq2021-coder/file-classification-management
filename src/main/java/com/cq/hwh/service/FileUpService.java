package com.cq.hwh.service;

import com.cq.hwh.domain.FileUp;
import com.cq.hwh.domain.FileUpExample;
import com.cq.hwh.mapper.FileUpMapper;
import com.cq.hwh.req.FileUpQueryReq;
import com.cq.hwh.resp.FileUpQueryResp;
import com.cq.hwh.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileUpService {

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

}
