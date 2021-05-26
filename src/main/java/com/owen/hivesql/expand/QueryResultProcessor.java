package com.owen.hivesql.expand;

import com.owen.hivesql.entity.QueryResult;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.expand
 * @ClassName: QueryResultProcesser
 * @Author: owen
 * @Description:
 * @Date: 2021/3/31 17:11
 * @Version: 1.0
 */
public interface QueryResultProcessor extends Processor<QueryResult>{

    @Override
    QueryResult eachQueryResultProcess(QueryResult queryResult);


    QueryResult headResultProcess(QueryResult queryResult);
}
