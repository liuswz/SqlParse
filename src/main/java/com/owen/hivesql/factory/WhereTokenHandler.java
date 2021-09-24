package com.owen.hivesql.factory;

import com.owen.hivesql.entity.WhereCondition;
import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.util.ParseSqlUtil;
import com.owen.hivesql.util.TokenUtils;
import com.owen.hivesql.hivecode.Node;
import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.HiveParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.factory
 * @ClassName: TableNameTokenHandler
 * @Author: owen
 * @Description:
 * @Date: 2021/3/31 18:47
 * @Version: 1.0
 */
public class WhereTokenHandler implements TokenHandler {
    List<WhereCondition> conList = new ArrayList<>();

    @Override
    public QueryResult tokenHandler(ASTNode ast, QueryResult qr) {
        checkCon(ast);
        qr.setWhereCon(conList);
        //设置where 子查询对应的索引
        if (qr.getSubQueryChildrens() != null) {
            int subQuerySize = qr.getSubQueryChildrens().size() - 1;
            for (int i = conList.size() - 1; i >= 0; i--) {
                if (conList.get(i).isHasSubQuery()) {
                    conList.get(i).setSubQueryIndex(subQuerySize--);
                }
            }
        }
        return qr;
    }

    // checkCon 根据不同条件解析
    public void checkCon(ASTNode ast) {
        if (ast.getText().equals("=") || ast.getText().equals(">") || ast.getText().equals("<") || ast.getText().equals("<=") || ast.getText().equals(">=") || ast.getText().equals("<>") || ast.getText().equals("!=")) {
            WhereCondition con = new WhereCondition();
            con.setCon(ast.getText());
            handlerEqual(ast, con);
            conList.add(con);
            //todo 可扩展，+between等
        } else if (ast.getText().equals("not")) {
            ASTNode tmp = (ASTNode) ast.getChild(0);
            if (tmp.getToken().getType() == HiveParser.TOK_FUNCTION && tmp.getChild(0).getText().equals("in")) {
                WhereCondition con = new WhereCondition();
                con.setCon("not in");
                handlerNotInCon(tmp, con);
                conList.add(con);
            }
        } else if (ast.getToken().getType() == HiveParser.TOK_FUNCTION && ast.getChild(0).getText().equals("in")) {
            WhereCondition con = new WhereCondition();
            con.setCon("in");
            handlerNotInCon(ast, con);
            conList.add(con);

        } else if (ast.getToken().getType() == HiveParser.KW_IN) {
            WhereCondition con = new WhereCondition();
            con.setCon("in");
            handlerInCon(ast, con);
            conList.add(con);
        } else if (ast.getText().equals("and") || ast.getText().equals("or")) {
            ArrayList<Node> children = ast.getChildren();
            for (Node child : children) {
                ASTNode astChild = (ASTNode) child;
                checkCon(astChild);
                if (conList.size() > 1) {
                    conList.get(conList.size() - 2).setNextCon(ast.getText());
                }
            }
        } else if (ast.getToken().getType() == HiveParser.TOK_QUERY) {
            //跳过
        } else {
            ArrayList<Node> children = ast.getChildren();
            if (children != null) {
                for (Node child : children) {
                    ASTNode astChild = (ASTNode) child;
                    checkCon(astChild);
                }
            }
        }
    }

    // handlerEqual 处理等号（= >=等）条件
    void handlerEqual(ASTNode ast, WhereCondition con) {
        ArrayList<Node> children = ast.getChildren();
        for (Node child : children) {
            ASTNode astChild = (ASTNode) child;
            if (astChild.getToken().getType() == HiveParser.TOK_TABLE_OR_COL) {
                con.setLeftValue(astChild.getChild(0).getText());
            } else if (astChild.getToken().getType() == HiveParser.TOK_SUBQUERY_EXPR) {
                con.setHasSubQuery(true);
            } else if (astChild.getToken().getType() == HiveParser.DOT) {
                con.setLeftValue(TokenUtils.getFieldOnDot(astChild));
            } else {
                con.addRightValue(astChild.getText());
            }
        }
    }

    // handlerInCon 处理in条件
    void handlerInCon(ASTNode ast, WhereCondition con) {
        ASTNode subQuery = (ASTNode) ast.getParent().getParent();
        for (Node child : subQuery.getChildren()) {
            ASTNode astChild = (ASTNode) child;
            if (astChild.getToken().getType() == HiveParser.TOK_TABLE_OR_COL) {
                con.setLeftValue(astChild.getChild(0).getText());
            } else if (astChild.getToken().getType() == HiveParser.TOK_QUERY) {
                con.setHasSubQuery(true);
            } else if (astChild.getToken().getType() == HiveParser.DOT) {
                con.setLeftValue(TokenUtils.getFieldOnDot(astChild));
            } else if (ParseSqlUtil.isNumber(astChild.getText())) {
                con.addRightValue(astChild.getText());
            }

        }
    }

    // handlerNotInCon 处理not in条件
    void handlerNotInCon(ASTNode ast, WhereCondition con) {
        for (Node child : ast.getChildren()) {
            ASTNode astChild = (ASTNode) child;
            if (astChild.getToken().getType() == HiveParser.TOK_TABLE_OR_COL) {
                con.setLeftValue(astChild.getChild(0).getText());
            } else if (astChild.getToken().getType() == HiveParser.TOK_QUERY) {
                con.setHasSubQuery(true);
            } else if (astChild.getToken().getType() == HiveParser.DOT) {
                con.setLeftValue(TokenUtils.getFieldOnDot(astChild));
            } else if (ParseSqlUtil.isNumber(astChild.getText())) {
                con.addRightValue(astChild.getText());
            }
        }
    }

}
