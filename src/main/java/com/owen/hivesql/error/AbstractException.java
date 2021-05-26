package com.owen.hivesql.error;


import com.owen.hivesql.enums.ErrorEnum;

/**
 * @ProjectName: aas
 * @Package: com.ke.bigdata.aas.commoninterface.error
 * @ClassName: AbstractException
 * @Author: liuwenzhuo
 * @Description: 抽象公共异常类
 * @Date: 2021/3/5 18:46
 * @Version: 1.0
 */
public abstract class AbstractException extends RuntimeException implements ErrorInfo{

    private ErrorEnum errorEnum;

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractException(Throwable cause) {
        super(cause);
    }

    public AbstractException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AbstractException(ErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorEnum = errorEnum;
    }


    @Override
    public  Integer getErrorCode() {
        return errorEnum.getErrorCode();
    }


    @Override
    public String getErrorMes() {
        return errorEnum.getErrorMsg();
    }
}
