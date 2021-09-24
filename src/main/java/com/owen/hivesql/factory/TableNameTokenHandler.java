package com.owen.hivesql.factory;

import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.hivecode.Node;
import com.owen.hivesql.hivecode.ASTNode;

import java.util.ArrayList;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.factory
 * @ClassName: TableNameTokenHandler
 * @Author: owen
 * @Description: 表名解析
 * @Date: 2021/3/31 18:47
 * @Version: 1.0
 */
public class TableNameTokenHandler implements TokenHandler{
    @Override
    public QueryResult tokenHandler(ASTNode head, QueryResult qr) {
        ASTNode ast  =(ASTNode) head.getChild(0);
        ArrayList<Node> children = ast.getChildren();
        StringBuffer sb = new StringBuffer();
        for(Node node:children){
            ASTNode astNode = (ASTNode)node;
            sb.append(astNode.getText());
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        qr.addTableName(sb.toString());
        if(head.getChildCount()>1) {
            qr.addTableAlias(head.getChild(1).getText());
        }else{
            qr.addTableAlias(null);
        }
        return qr;
    }
}
