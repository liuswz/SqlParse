package com.owen.hivesql.factory;

import com.owen.hivesql.entity.Field;
import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.util.TokenUtils;
import com.owen.hivesql.hivecode.Node;
import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.HiveParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.factory
 * @ClassName: TableNameTokenHandler
 * @Author: owen
 * @Description: 对select 的字段进行解析
 * @Date: 2021/3/31 18:47
 * @Version: 1.0
 */
public class FieldsTokenHandler implements TokenHandler {
    @Override
    public QueryResult tokenHandler(ASTNode ast, QueryResult qr) {
        if (ast.getToken().getType() == HiveParser.TOK_SELECTDI) {
            qr.setIfDistinct(true);
        }
        ArrayList<Node> children = ast.getChildren();
        List<Field> fields = new ArrayList<>();
        for (Node node : children) {
            ASTNode astNode = (ASTNode) node;
            fields.add(getField(astNode));
        }
        qr.setFields(fields);
        return qr;
    }

    // getField 获取一个ASTNode的所有字段
    public Field getField(ASTNode head) {
        //  ast
        Field field = new Field();
        int c = head.getChildCount();
        if (c > 1) {
            field.setAlias(head.getChild(c - 1).getText());
        }
        ASTNode child = (ASTNode) head.getChild(0);
        if (child.getToken().getType() == HiveParser.DOT) {
            field.setName(TokenUtils.getFieldOnDot(child));
            field.addField(field.getName());
        } else if (child.getToken().getType() == HiveParser.TOK_FUNCTION) {
            field.setFunc(child.getChild(0).getText());
            field.setName("function_" + field.getFunc());
            field.setStringTree(child.toStringTree());
            field = getAllFields(child, field);
        } else if (child.getToken().getType() == HiveParser.TOK_FUNCTIONDI) {
            field.setFunc(child.getChild(0).getText() + "_distinct");
            field.setName("function_" + field.getFunc() + "_distinct");
            field.setStringTree(child.toStringTree());
            field = getAllFields(child, field);
        } else if (child.getToken().getType() == HiveParser.TOK_ALLCOLREF) {
            field.setName("*");
        } else if (child.getToken().getType() == HiveParser.TOK_TABLE_OR_COL) {
            field.setName(child.getChild(0).getText());
            field.addField(field.getName());
        } else if (child.getText().equals("+") || child.getText().equals("-") || child.getText().equals("*") || child.getText().equals("/")) {
            field.setFunc(child.getText());
            field.setName("operator_" + field.getFunc());
            field.setStringTree(child.toStringTree());
            getOpFields(child);
            field.addFields(opFieldList);
        } else {
            if (child.getChild(0) == null) {
                field.setName(child.getText());
                field.addField(field.getName());
            } else {
                field.setName(child.getChild(0).getText());
                field.addField(field.getName());
            }
        }
        return field;
    }

    List<String> opFieldList = new ArrayList<>();

    // getOpFields获取 操作的字段 +,-,*,/ 的字段
    public void getOpFields(ASTNode ast) {
        ArrayList<Node> children = ast.getChildren();
        if (children != null) {
            for (Node child : children) {
                ASTNode astChild = (ASTNode) child;
                //todo 可以扩展TOK_SUBQUERY_EXPR
                if (astChild.getToken().getType() == HiveParser.TOK_TABLE_OR_COL) {
                    opFieldList.add(astChild.getChild(0).getText());
                } else if (astChild.getToken().getType() == HiveParser.DOT) {
                    opFieldList.add(TokenUtils.getFieldOnDot(astChild));
                } else {
                    getOpFields(astChild);
                }

            }
        }

    }

    // getAllFields 当字段有一些嵌套时，如if(a>b,c,d)， 递归获取所有包括的字段 a,b,c,d
    public Field getAllFields(ASTNode ast, Field field) {
        int level = 5;
        Queue<ASTNode> queue = new LinkedList<ASTNode>();
        queue.offer(ast);

        while (!queue.isEmpty()) {
            ASTNode tmp = queue.poll();
            ArrayList<Node> children = tmp.getChildren();
            if (tmp.getToken().getType() == HiveParser.TOK_TABLE_OR_COL) {
                for (Node node : children) {
                    ASTNode astNode = (ASTNode) node;
                    field.addField(astNode.getText());
                }
            } else if (tmp.getToken().getType() == HiveParser.DOT) {
                field.addField(TokenUtils.getFieldOnDot(tmp));
                continue;
            }
            if (children != null) {
                if (level > 0) {
                    for (Node node : children) {
                        ASTNode child = (ASTNode) node;
                        queue.offer(child);
                    }
                }
                level--;
            }

        }
        return field;
    }

    public String getFieldByDot(ASTNode ast) {
        ArrayList<Node> children = ast.getChildren();
        StringBuffer sb = new StringBuffer();

        for (Node node : children) {
            ASTNode astNode = (ASTNode) node;
            sb.append(astNode.getText());
            sb.append(".");

        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
