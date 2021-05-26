package com.owen.hivesql.util;



import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.ParseDriver;
import com.owen.hivesql.hivecode.ParseException;

import java.math.BigDecimal;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.util
 * @ClassName: ParseSqlUtil
 * @Author: owen
 * @Description:
 * @Date: 2021/3/30 18:13
 * @Version: 1.0
 */
public class ParseSqlUtil {
    public static boolean checkIfSelect(ASTNode ast){
        return ast.getChild(1).getChild(0).getChild(0).getChild(0).toString().contains("TMP");
    }
    public static ASTNode getAstTree(String sql) throws ParseException {
        ASTNode tree = new ParseDriver().parse(sql, true);
        while ((tree.getToken() == null) && (tree.getChildCount() > 0)) {
            tree = (ASTNode) tree.getChild(0);
        }
        return tree;
    }

    public static boolean isNumber(String num){
        String bigStr;
        try {
            bigStr = new BigDecimal(num).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isNumber("20210411000000"));
    }

}
