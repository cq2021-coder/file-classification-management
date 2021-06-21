package com.cq.hwh.mapper;

import com.cq.hwh.domain.FileUp;
import com.cq.hwh.domain.FileUpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileUpMapper {
    long countByExample(FileUpExample example);

    int deleteByExample(FileUpExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileUp record);

    int insertSelective(FileUp record);

    List<FileUp> selectByExample(FileUpExample example);

    FileUp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileUp record, @Param("example") FileUpExample example);

    int updateByExample(@Param("record") FileUp record, @Param("example") FileUpExample example);

    int updateByPrimaryKeySelective(FileUp record);

    int updateByPrimaryKey(FileUp record);
}