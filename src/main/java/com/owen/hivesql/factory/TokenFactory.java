package com.owen.hivesql.factory;

import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.HiveParser;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree
 * @ClassName: TokenFactory
 * @Author: owen
 * @Description:
 * @Date: 2021/3/31 18:40
 * @Version: 1.0
 */
public class TokenFactory {

    public static TokenHandler handlerASTNode(ASTNode ast) {
        switch(ast.getToken().getType() ){

            case HiveParser.TOK_SELECT :
            case HiveParser.TOK_SELECTDI:
                return new FieldsTokenHandler();

            case HiveParser.TOK_TABREF :
                return new TableNameTokenHandler();
            case HiveParser.TOK_WHERE:
                return new WhereTokenHandler();
            case HiveParser.TOK_HAVING:

                return new HavingTokenHandler();

            case HiveParser.TOK_JOIN :
            case HiveParser.TOK_LEFTOUTERJOIN:
            case HiveParser.TOK_RIGHTOUTERJOIN:
            case HiveParser.TOK_FULLOUTERJOIN:
                return new OnTokenHandler();
            case HiveParser.TOK_LIMIT:
                return new LimitTokenHandler();
            case HiveParser.TOK_GROUPBY:
                return new GroupByTokenHandler();
            case HiveParser.TOK_TABSORTCOLNAMEASC:
                return new OrderByTokenHandler();
            case HiveParser.TOK_TAB:
                return new InsertTableNameTokenHandler();
            case HiveParser.TOK_UNIONALL:
                return new UnionAllTokenHandler();
            //你可以有任意数量的case语句
            default : //可选
                //语句
        }
        return null;
    }
}
