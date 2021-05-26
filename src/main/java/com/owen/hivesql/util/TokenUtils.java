package com.owen.hivesql.util;

import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.HiveParser;
import com.owen.hivesql.hivecode.Node;


import java.util.ArrayList;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.util
 * @ClassName: TokenUtils
 * @Author: owen
 * @Description:
 * @Date: 2021/4/2 17:35
 * @Version: 1.0
 */
public class TokenUtils {

    public  static  String getFieldOnDot(ASTNode ast){
        ArrayList<Node> children = ast.getChildren();
        StringBuffer sb = new StringBuffer();;
        for(Node node:children){
            ASTNode astNode = (ASTNode)node;
            if(astNode.getToken().getType()== HiveParser.TOK_TABLE_OR_COL){
                sb.append(astNode.getChild(0).getText());
            }else{
                sb.append(astNode.getText());
            }

            sb.append(".");

        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }


}
