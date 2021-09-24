//package com.owen.hivesql.expand.processor;
//
//import com.owen.hivesql.entity.QueryResult;
//import com.owen.hivesql.expand.QueryResultProcessor;
//
///**
// * @ProjectName: Sql
// * @Package: com.owen.parseasttree.expand
// * @ClassName: QueryResultProcesser
// * @Author: owen
// * @Description:
// * @Date: 2021/3/31 17:11
// * @Version: 1.0
// */
//public class TestQueryResultProcessor implements QueryResultProcessor {
//
//
//    @Override
//    public QueryResult eachQueryResultProcess(QueryResult queryResult) {
//
//        return queryResult;
//    }
//
//    @Override
//    public QueryResult headResultProcess(QueryResult queryResult) {
//        System.out.println(queryResult.getSqlType().getName()+"----------------");
//        if(queryResult.getSqlType().getName().equals("select")){
//            throw new RuntimeException("ssssssssssssss");
//        }
//        return queryResult;
//    }
//}
