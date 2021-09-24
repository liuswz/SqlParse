package com.owen.hivesql.expand;

import com.owen.hivesql.entity.QueryResult;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.expand
 * @ClassName: QueryResultProcesser
 * @Author: owen
 * @Description: 基础扩展接口，每个子查询转换成QueryResult时都会执行此方法
 * @Date: 2021/3/31 16:22
 * @Version: 1.0
 */
public interface Processor<T> {
    public QueryResult eachQueryResultProcess(QueryResult queryResult);
}
