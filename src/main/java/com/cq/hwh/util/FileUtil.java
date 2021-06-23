package com.cq.hwh.util;

import com.cq.hwh.exception.BusinessException;
import com.cq.hwh.exception.BusinessExceptionCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static String upFile(MultipartFile file,String reqName){
        if (file==null){
            throw new BusinessException(BusinessExceptionCode.FILE_UP_ERROR);
        }
        String fileName = file.getOriginalFilename();
        String name =fileName.substring(0, fileName.indexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = name+"_"+System.currentTimeMillis()+"."+suffix;
        String basePath= PathUtil.getResultPath();
        File allpath=new File(basePath+reqName);
        if(!allpath.exists()){
            allpath.mkdirs();
        }
        try{
            file.transferTo(new File(allpath+"/"+newFileName));
        }catch (IOException e){
            e.getMessage();
        }
        return reqName+"/"+newFileName;
    }
}
