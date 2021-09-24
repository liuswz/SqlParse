package com.owen.hivesql.exception;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.exception
 * @ClassName: parseSqlException
 * @Author: owen
 * @Description: 自定义异常，在sql解析中出现的错误
 * @Date: 2021/3/31 17:30
 * @Version: 1.0
 */
public class parseRunningException  extends Exception{
    public parseRunningException(String message) {
        super(message);
    }
}
