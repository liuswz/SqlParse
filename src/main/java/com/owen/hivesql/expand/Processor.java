package com.owen.hivesql.expand;

import com.owen.hivesql.entity.QueryResult;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.expand
 * @ClassName: QueryResultProcesser
 * @Author: owen
 * @Description:
 * @Date: 2021/3/31 16:22
 * @Version: 1.0
 */
public interface Processor<T> {

    public QueryResult eachQueryResultProcess(QueryResult queryResult);

}
