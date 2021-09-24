package com.owen.hivesql.entity;

import com.owen.hivesql.enums.SqlType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.entity
 * @ClassName: QueryResult
 * @Author: owen
 * @Description: 子查询，也是最终的结果
 * @Date: 2021/3/30 17:16
 * @Version: 1.0
 */
@Data
public class QueryResult {
    SqlType sqlType; //sql 类型 select、insert、create等
    List<String> tableNames; //表名
    List<String> tableAlias; //表别名
    String insertTableName;  //如果是insert的话插入的表名
    List<Field> fields; //select 的字段
    Boolean wheHead; //是否是sql的最外层
    List<QueryResult> subQueryChildrens; //子查询 如select id,name from t where id in (select id form t1)
    List<QueryResult> joinChildrens; //关联子查询 join、 left join
    List<QueryResult> unionChildrens; //union、union all 子查询
    List<String> groupbyValue; //group by 条件
    List<String> orderbyValue; //order by 条件
    List<WhereCondition> whereCon; //where 条件
    //on 条件， 因为可能有多个join条件，所以是List<List<OnCondition>>
    //如 select a,b from t1 join t2 on t1.id = t2.id and t1.name = t2.name join t3 on t1.id = t3.id and t1.name = t3.name
    List<List<OnCondition>> onCons;
    List<HavingCondition> havingCon; //having条件
    Integer limitCon; //limit条件
    List<String> subTableAlias; //子查询别名 如select id from (select id from t1)tt 的tt
    List<String> joinTypes; //join 类型，join、left join、full join等
    List<String> unionTypes; //union类型 有union、和union all
    Map<String,QueryResult > withSql; //with as 语句，key为别名，value是with子查询
    boolean ifDistinct; //字段是否是distinct
    Map<String,Object > selfValue; //自定义属性，用户扩展时可以使用

    public QueryResult() {
        initValue();
    }

    public void addWithSql(String key,QueryResult rs){
        if(withSql==null) {withSql = new HashMap<>();}
        withSql.put(key,rs);
    }

    public void addTableAlias(String alias){
        if(tableAlias==null) {tableAlias = new ArrayList<>();}
        tableAlias.add(alias);
    }

    public void addUnionTypes(String unionType){
        if(unionTypes==null) {unionTypes = new ArrayList<>();}
        unionTypes.add(unionType);
    }

    public void addJoinTypes(String joinType){
        if(joinTypes==null) {joinTypes = new ArrayList<>();}
        joinTypes.add(joinType);
    }

    public void addOnCons(List<OnCondition> onCon){
        if(onCons==null) {onCons = new ArrayList<>();}
        onCons.add(onCon);
    }

    public void addGroupbyValue(String value){
        if(groupbyValue==null) {groupbyValue = new ArrayList<>();}
        groupbyValue.add(value);
    }

    public void addOrderbyValue(String value){
        if(orderbyValue==null) {orderbyValue = new ArrayList<>();}
        orderbyValue.add(value);
    }

    public void addSubTableAlias(String alias){
        if(subTableAlias==null) {subTableAlias = new ArrayList<>();}
        subTableAlias.add(alias);
    }

    public void addJoinChildren(QueryResult child){
        if(joinChildrens==null) {joinChildrens = new ArrayList<>();}
        joinChildrens.add(child);
    }

    public void addUnionChildren(QueryResult child){
        if(unionChildrens==null) {unionChildrens = new ArrayList<>();}
        unionChildrens.add(child);
    }

    public void addSubQueryChildrens(QueryResult child){
        if(subQueryChildrens==null) {subQueryChildrens = new ArrayList<>();}
        subQueryChildrens.add(child);
    }

    public void addTableName(String tableName){
        if(tableNames==null) {tableNames = new ArrayList<>();}
        tableNames.add(tableName);
    }

    public void initValue(){
        sqlType = SqlType.SELECT;
        insertTableName = "";
        ifDistinct = false;
        wheHead = false;
    }
}
