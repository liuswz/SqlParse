package com.owen.hivesql.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.entity
 * @ClassName: Field
 * @Author: owen
 * @Description: 字段
 * @Date: 2021/4/1 17:05
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class Field {
    String name; //字段名，如果是函数function_函数名，有操作符号的（+ - *） 为 operator_
    List<String> fields; //字段中包含的所有属性，如select if(a=1 ,b,c) 中的a、b、c 会有t.a
    String alias; //字段别名 as ...
    String func; //如果是函数，函数名 if  todo 考虑多个函数
    String stringTree; //子语法树，如不需要可忽略 nvl(if())

    public void addField(String field){
        if(fields==null) {fields = new ArrayList<>();}
        fields.add(field);
    }

    public void addFields(List<String> fieldList){
        if(fields==null) {fields = new ArrayList<>();}
        fields.addAll(fieldList);
    }
}
