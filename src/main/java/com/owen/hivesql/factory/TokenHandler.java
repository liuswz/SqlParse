package com.owen.hivesql.factory;

import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.hivecode.ASTNode;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.factory
 * @ClassName: TokenHandler
 * @Author: owen
 * @Description:
 * @Date: 2021/3/31 18:43
 * @Version: 1.0
 */
public interface TokenHandler {
    public QueryResult tokenHandler(ASTNode ast,QueryResult qr);
}
