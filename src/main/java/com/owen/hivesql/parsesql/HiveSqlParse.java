package com.owen.hivesql.parsesql;

import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.entity.Settings;
import com.owen.hivesql.exception.parseRunningException;
import com.owen.hivesql.handletree.SemanticAnalyzer;
import com.owen.hivesql.util.ParseSqlUtil;
import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.ParseException;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.handletree
 * @ClassName: ParseHiveSql
 * @Author: owen
 * @Description: 解析sql的主函数
 * @Date: 2021/4/3 21:13
 * @Version: 1.0
 */
public class HiveSqlParse {

    public QueryResult parseSql(String sql) throws ParseException, parseRunningException {
        //获取hive语法树
        ASTNode tree = ParseSqlUtil.getAstTree(sql);
        //实例化解析器
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        //拿到解析结果queryResult
        QueryResult queryResult = semanticAnalyzer.handlerHeadTree(tree);

        return queryResult;
    }

    // setProcessorPackage 设置扩展类的包名
    public void setProcessorPackage(String packageName) {
        Settings.packageName = packageName;
    }
}
