package test;

//import antlr.HplsqlLexer;
//import antlr.HplsqlParser;
//import org.antlr.v4.runtime.ANTLRInputStream;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.antlr.v4.runtime.tree.ParseTree;


import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.exception.parseRunningException;
import com.owen.hivesql.handletree.SemanticAnalyzer;
import com.owen.hivesql.util.ParseSqlUtil;
import com.owen.hivesql.util.TokProcessor;
import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.ParseException;

import java.io.IOException;

/**
 * @ProjectName: Sql
 * @Package: test
 * @ClassName: Test1
 * @Author: owen
 * @Description: 测试类
 * @Date: 2021/3/29 18:40
 * @Version: 1.0
 */
public class Test1 {

    static String sql = "with t as (  select \n" +
            "      id,name  \n" +
            "   from table1)\n" +
            "\n" +
            "select \n" +
            "   if(id>name,'a','b'),name,company \n" +
            "from \n" +
            "t\n" +
            "join \n" +
            "(\n" +
            "   select \n" +
            "      id,company \n" +
            "   from table2 \n" +
            ")t2\n" +
            "on t1.id = t2.id";


    public static void main(String[] args) throws IOException {

        System.out.println(sql);
        QueryResult queryResult = null;
        try {
            // ASTNode tree = ParseUtils.parse(sql, null);
            ASTNode tree = ParseSqlUtil.getAstTree(sql);
            // System.out.println(tree.toStringTree());
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();

            queryResult = semanticAnalyzer.handlerHeadTree(tree);

            System.out.println(queryResult);

//            ASTNode child = (ASTNode)tree.getChild(0).getChild(0).getChild(0).getChild(0).getChild(1).getChild(1).getChild(0).getChild(0);
//            System.out.println(child.toStringTree());
//            System.out.println(child.getToken().getText());

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (parseRunningException e) {
            e.printStackTrace();
        }


    }
}
