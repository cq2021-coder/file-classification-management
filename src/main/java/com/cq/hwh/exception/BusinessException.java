package com.cq.hwh.exception;

public class BusinessException extends RuntimeException{

    private com.cq.hwh.exception.BusinessExceptionCode code;

    public BusinessException (com.cq.hwh.exception.BusinessExceptionCode code) {
        super(code.getDesc());
        this.code = code;
    }

    public com.cq.hwh.exception.BusinessExceptionCode getCode() {
        return code;
    }

    public void setCode(com.cq.hwh.exception.BusinessExceptionCode code) {
        this.code = code;
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
