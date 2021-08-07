package com.cq.hwh.exception;

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("登录名已存在"),
    LOGIN_USER_ERROR("用户名不存在或密码错误"),
    RESET_USER_ERROR("用户名不存在"),
    FILE_UP_ERROR("上传文件不能为空"),
    CATEGORY_ERROR("类型已存在"),
    FILE_MORE_ERROR("文件已存在"),
    FILE_NULL_ERROR("文件不存在"),
    FILE_DELETE_ERROR("文件删除失败"),
    TOKEN_NULL("无令牌，请重新登录"),
    TOKEN_VERIFY_ERROR("令牌验证错误！"),
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
