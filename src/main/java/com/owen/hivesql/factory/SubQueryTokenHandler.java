package com.owen.hivesql.factory;

import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.hivecode.ASTNode;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.factory
 * @ClassName: TableNameTokenHandler
 * @Author: owen
 * @Description:
 * @Date: 2021/3/31 18:47
 * @Version: 1.0
 */
public class SubQueryTokenHandler implements TokenHandler{
    @Override
    public QueryResult tokenHandler(ASTNode ast, QueryResult qr) {
//        QueryResult qrHead = new QueryResult();
//
//        qrHead.addChild(qr);
        return qr;
    }

}
