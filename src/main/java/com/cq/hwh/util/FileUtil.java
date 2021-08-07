package com.cq.hwh.util;

import com.cq.hwh.exception.BusinessException;
import com.cq.hwh.exception.BusinessExceptionCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 *
 * @author 程崎
 * @date 2021/06/26
 */
public class FileUtil {
    /**
     * 上传文件
     *
     * @param file    文件
     * @param reqName 要求的名字
     * @return {@link String}
     */
    public static String upFile(MultipartFile file,String reqName,String categoryName){
        if (file==null){
            throw new BusinessException(BusinessExceptionCode.FILE_UP_ERROR);
        }
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = reqName+"."+suffix;
        String basePath= PathUtil.getResultPath();
        File allPath=new File(basePath+"/"+categoryName);
        if(!allPath.exists()){
            allPath.mkdirs();
        }
        try{
            file.transferTo(new File(allPath+"/"+newFileName));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return "file/loveHWH/"+categoryName+"/"+newFileName;
    }
}
