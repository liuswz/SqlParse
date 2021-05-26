package com.owen.hivesql.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.entity
 * @ClassName: Condition
 * @Author: owen
 * @Description: 条件实体类的父类
 * @Date: 2021/4/2 11:47
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
public class Condition {

    String leftValue; //左值
    String con; //条件 = > in等
    List<String> rightValue; //条件右值、 因为可能有in 如 id in （1,2,3）。所以可能有多个值，用list

    String nextCon; //多个条件的关联条件，如 a.id = 1 or a.name = 'owen' 中的or

    public void addCon(String v){
        if(con==null) con="";
        con = con+" "+ v;
    }
    public void addRightValue(String right){
        if(rightValue==null) rightValue = new ArrayList<>();
        rightValue.add(right);
    }
}
