package com.owen.hivesql.enums;

import lombok.Getter;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.enums
 * @ClassName: SqlType
 * @Author: owen
 * @Description:
 * @Date: 2021/3/30 17:17
 * @Version: 1.0
 */

public enum  SqlType {
    SELECT( "select"),
    INSERT( "insert"),
    CREATETABLE( "createtable"),
    CREATEVIEW( "createview"),
    OTHERS( "others"),
   ;

    @Getter
    private String name;

    SqlType(String name) {
        this.name = name;

    }
}
