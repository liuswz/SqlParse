package com.owen.hivesql.handletree;

import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.entity.Settings;
import com.owen.hivesql.enums.SqlType;
import com.owen.hivesql.exception.parseRunningException;
import com.owen.hivesql.expand.QueryResultProcessor;
import com.owen.hivesql.factory.TokenFactory;
import com.owen.hivesql.factory.TokenHandler;
import com.owen.hivesql.util.ParseSqlUtil;
import com.owen.hivesql.util.ProcesserUtil;
import org.antlr.runtime.tree.Tree;
import com.owen.hivesql.hivecode.Node;
import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.HiveParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.handletree
 * @ClassName: SemanticAnalyzer
 * @Author: owen
 * @Description: 语法解析类
 * @Date: 2021/3/30 15:01
 * @Version: 1.0
 */
public class SemanticAnalyzer {

    QueryResult subQr = new QueryResult();
    QueryResult headQr = new QueryResult();
    Stack<QueryResult> tmpQr = new Stack();

    List<QueryResultProcessor> selfProcessors;
    List<QueryResultProcessor> allProcessors;

    // handlerHeadTree 根据不同的 sql类型解析 如create table、create view、 select等
    public QueryResult handlerHeadTree(ASTNode head) throws parseRunningException {
        if (head.getToken().getType() == HiveParser.TOK_CREATETABLE) {
            headQr.setSqlType(SqlType.CREATETABLE);
            handlerHeadExpand();
            return headQr;
        }
        //处理create view 命令
        else if (head.getToken().getType() == HiveParser.TOK_CREATEVIEW ||
                (head.getToken().getType() == HiveParser.TOK_ALTERVIEW &&
                        head.getChild(1).getType() == HiveParser.TOK_QUERY)) {
            headQr.setSqlType(SqlType.CREATEVIEW);
            handlerHeadExpand();
            return headQr;
        } else if (head.getToken().getType() == HiveParser.TOK_QUERY) {
            //select 语句
            if (ParseSqlUtil.checkIfSelect((head))) {
                headQr.setSqlType(SqlType.SELECT);
            } else {
                //insert 语句
                headQr.setSqlType(SqlType.INSERT);
            }
            handlerHeadExpand();
            int num = getWithSqlNum(head);
            recursionAstTree(head);
            subQr.setSqlType(headQr.getSqlType());
            subQr.setSelfValue(headQr.getSelfValue());
            subQr.setWheHead(true);
            if (num > 0) {
                subQr = convertWithSql(subQr, num);
            }
            handlerExpand();
            return subQr;
        } else {
            headQr.setSqlType(SqlType.OTHERS);
            handlerHeadExpand();
            return headQr;
        }
    }

    public QueryResult convertWithSql(QueryResult qr, int num) {
        List<QueryResult> joinChildrens = qr.getJoinChildrens();
        List<String> tableAlias = qr.getSubTableAlias();
        for (int i = 0; i < num; i++) {

            qr.addWithSql(tableAlias.get(tableAlias.size() - 1), joinChildrens.get(joinChildrens.size() - 1));
            tableAlias.remove(tableAlias.size() - 1);
            joinChildrens.remove(joinChildrens.size() - 1);
        }
        qr.setSubTableAlias(tableAlias);
        qr.setJoinChildrens(joinChildrens);

        return qr;
    }

    public int getWithSqlNum(ASTNode ast) {
        if (ast.getChildCount() > 2 && ast.getChild(2).getType() == HiveParser.TOK_CTE) {
            return ast.getChild(2).getChildCount();
        }
        return 0;
    }

    int i = 0;

    // recursionAstTree 递归遍历解析整个树
    public void recursionAstTree(ASTNode ast) {
        ArrayList<Node> children = ast.getChildren();
        if (children != null) {
            for (Node child : children) {
                ASTNode astChild = (ASTNode) child;
                //todo 可以扩展TOK_SUBQUERY_EXPR
                if ((astChild.getToken().getType() == HiveParser.TOK_QUERY && astChild.getParent().getParent() != null)) {
                    tmpQr.push(subQr);
                    subQr = new QueryResult();
                }
                recursionAstTree(astChild);
            }
            if (ast.getToken().getType() == HiveParser.TOK_QUERY && ast.getParent().getParent() != null) {
                handlerExpand();
                ASTNode parent = (ASTNode) ast.getParent();
                if (parent.getToken().getType() == HiveParser.TOK_SUBQUERY && ast.getChild(0).getChild(0).getChild(0).getType() != HiveParser.TOK_UNIONALL) {
                    QueryResult pop = tmpQr.pop();
                    pop.addJoinChildren(subQr);
                    subQr = pop;
                    if (parent.getChildCount() > 1) {
                        subQr.addSubTableAlias(parent.getChild(parent.getChildCount() - 1).getText());
                    }
                } else if (parent.getToken().getType() == HiveParser.TOK_SUBQUERY_EXPR) {
                    QueryResult pop = tmpQr.pop();
                    pop.addSubQueryChildrens(subQr);
                    subQr = pop;
                } else if (parent.getToken().getType() == HiveParser.TOK_UNIONALL && ast.getChild(0).getChild(0).getType() != HiveParser.TOK_SUBQUERY) {
                    QueryResult pop = tmpQr.pop();
                    pop.addUnionChildren(subQr);
                    subQr = pop;
                }
            }
            TokenHandler th = TokenFactory.handlerASTNode(ast);
            if (th != null) {
                subQr = th.tokenHandler(ast, subQr);
            }
        }
    }

    final String selfPackageName = "com.owen.parseasttree.expand.processor";

    // handlerHeadExpand 处理sql第一层 的扩展类
    public void handlerHeadExpand() throws parseRunningException {
        selfProcessors = ProcesserUtil.getAllProcessor(selfPackageName);
        selfProcessors.forEach(processor -> headQr = processor.headResultProcess(headQr));

        if (Settings.packageName != null) {
            allProcessors = ProcesserUtil.getAllProcessor(Settings.packageName);
            allProcessors.forEach(processor -> headQr = processor.headResultProcess(headQr));
        }
    }

    // handlerExpand 处理sql每个子查询的结果 的扩展类
    public void handlerExpand() {
        selfProcessors.forEach(processor -> subQr = processor.eachQueryResultProcess(subQr));

        if (allProcessors != null) {
            allProcessors.forEach(processor -> subQr = processor.eachQueryResultProcess(subQr));
        }
    }

}
