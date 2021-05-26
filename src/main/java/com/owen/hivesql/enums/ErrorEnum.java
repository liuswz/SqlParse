package com.owen.hivesql.enums;

/**
 * @ProjectName: aas
 * @Package: com.ke.bigdata.aas.entity.enums
 * @ClassName: ErrorEnum
 * @Author: liuwenzhuo
 * @Description: ${description}
 * @Date: 2021/3/5 18:07
 * @Version: 1.0
 */
public enum ErrorEnum {

    E_601(601, "查询不到数据"),;

    private Integer errorCode;
    private String errorMsg;
    ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public Integer getErrorCode() {
        return errorCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
}
