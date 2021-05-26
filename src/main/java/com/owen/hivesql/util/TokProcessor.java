package com.owen.hivesql.util;

import com.owen.hivesql.hivecode.Node;
import com.owen.hivesql.hivecode.ASTNode;

import java.util.ArrayList;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.util
 * @ClassName: TokProcessor
 * @Author: owen
 * @Description:
 * @Date: 2021/3/31 18:22
 * @Version: 1.0
 */
public class TokProcessor {
    public static String tableNameHandler(ASTNode ast){
        ArrayList<Node> children = ast.getChildren();
        StringBuffer sb = new StringBuffer();
        for(Node node:children){
            ASTNode astNode = (ASTNode)node;
            sb.append(astNode.getText());
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
