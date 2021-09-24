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
 * @Description: 解析sql工具类
 * @Date: 2021/3/30 18:13
 * @Version: 1.0
 */
public class ParseSqlUtil {

    // checkIfSelect 校验是否是select
    public static boolean checkIfSelect(ASTNode ast) {
        return ast.getChild(1).getChild(0).getChild(0).getChild(0).toString().contains("TMP");
    }

    // getAstTree 获取ASTTree
    public static ASTNode getAstTree(String sql) throws ParseException {
        ASTNode tree = new ParseDriver().parse(sql, true);
        while ((tree.getToken() == null) && (tree.getChildCount() > 0)) {
            tree = (ASTNode) tree.getChild(0);
        }
        return tree;
    }

    // isNumber 校验是否是数字
    public static boolean isNumber(String num) {
        String bigStr;
        try {
            bigStr = new BigDecimal(num).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }
}
