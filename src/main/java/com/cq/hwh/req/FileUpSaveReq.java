package com.cq.hwh.req;

import javax.validation.constraints.NotNull;

public class FileUpSaveReq {
    @NotNull(message = "文件名不能为空")
    private String name;

    @NotNull(message = "类型名不能为空")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileUpSaveReq{" +
                ", name='" + name + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}