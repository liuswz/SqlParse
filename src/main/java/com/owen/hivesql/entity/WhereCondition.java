package com.owen.hivesql.entity;

import lombok.Data;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.entity
 * @ClassName: WhereCondition
 * @Author: owen
 * @Description: where条件
 * @Date: 2021/4/2 16:50
 * @Version: 1.0
 */
@Data
public class WhereCondition extends Condition{
    boolean HasSubQuery;//是否有子查询 如 where id in （select id from t1）
    Integer subQueryIndex ; //如果有子查询 QueryResult中的subQueryChildrens的索引。
    public WhereCondition() {
        HasSubQuery = false;
    }
}
