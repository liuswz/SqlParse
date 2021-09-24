package com.owen.hivesql.factory;

import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.hivecode.ASTNode;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.factory
 * @ClassName: TableNameTokenHandler
 * @Author: owen
 * @Description: limit解析
 * @Date: 2021/3/31 18:47
 * @Version: 1.0
 */
public class LimitTokenHandler implements TokenHandler {
    @Override
    public QueryResult tokenHandler(ASTNode ast, QueryResult qr) {
        qr.setLimitCon(Integer.parseInt(ast.getChild(0).getText()));
        return qr;
    }
}
