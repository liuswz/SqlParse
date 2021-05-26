package com.owen.hivesql.factory;

import com.owen.hivesql.entity.QueryResult;
import org.antlr.runtime.tree.Tree;
import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.HiveParser;

/**
 * @ProjectName: Sql
 * @Package: com.owen.hivesql.factory
 * @ClassName: UnionAllTokenHandler
 * @Author: owen
 * @Description:
 * @Date: 2021/4/14 18:19
 * @Version: 1.0
 */
public class UnionAllTokenHandler implements TokenHandler{
    @Override
    public QueryResult tokenHandler(ASTNode ast, QueryResult qr) {
        Tree parent = ast.getParent().getParent().getParent().getParent();
        if(parent.getType()== HiveParser.TOK_SUBQUERY){
            qr.addUnionTypes("union");
        }else{
            qr.addUnionTypes("union all");
        }

        return qr;
    }
}
