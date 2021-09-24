package com.owen.hivesql.expand;

import com.owen.hivesql.entity.QueryResult;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.expand
 * @ClassName: QueryResultProcesser
 * @Author: owen
 * @Description: 基础扩展接口之上的扩展接口 当判断完sql语句的类型时执行次方法，可以用来过滤sql的类型（select insert update等）
 * @Date: 2021/3/31 17:11
 * @Version: 1.0
 */
public interface QueryResultProcessor extends Processor<QueryResult>{

    @Override
    QueryResult eachQueryResultProcess(QueryResult queryResult);

    QueryResult headResultProcess(QueryResult queryResult);
}
