package com.owen.hivesql.factory;

import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.util.TokenUtils;
import com.owen.hivesql.hivecode.Node;
import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.HiveParser;

import java.util.ArrayList;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.factory
 * @ClassName: TableNameTokenHandler
 * @Author: owen
 * @Description: groupby字段的解析
 * @Date: 2021/3/31 18:47
 * @Version: 1.0
 */
public class GroupByTokenHandler implements TokenHandler {
    @Override
    public QueryResult tokenHandler(ASTNode ast, QueryResult qr) {
        ArrayList<Node> children = ast.getChildren();
        for (Node child : children) {
            ASTNode astChild = (ASTNode) child;
            if (astChild.getToken().getType() == HiveParser.TOK_TABLE_OR_COL) {
                qr.addGroupbyValue(astChild.getChild(0).getText());
            } else if (astChild.getToken().getType() == HiveParser.DOT) {
                qr.addGroupbyValue(TokenUtils.getFieldOnDot(astChild));
            } else {
                qr.addGroupbyValue(astChild.getChild(0).getText());
            }
        }
        return qr;
    }

}
