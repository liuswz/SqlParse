package com.owen.hivesql.error;

/**
 * @ProjectName: aas
 * @Package: com.ke.bigdata.aas.commoninterface.error
 * @ClassName: ErrorInfo
 * @Author: liuwenzhuo
 * @Description: 公共接口，能获取错误描述和错误码.
 * @Date: 2021/3/5 18:19
 * @Version: 1.0
 */
public interface ErrorInfo {
    /**
     * 获取异常对应的错误码.
     *
     * @return 错误码系统定义的错误码
     */
    Integer getErrorCode();

    /**
     * 获取错误码对应的描述信息.
     * <p>
     *   如果本地对该描述信息进行设置,则本地的优先,否则自动从错误码系统获取错误码对应的描述信息.
     * </p>
     * @return 错误码对应的描述信息
     */
    String getErrorMes();
}
