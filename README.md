# Hive Sql解析器

实现hive sql解析，可以准确的解析复杂的sql语句，获取sql包括子句等所有结构化的信息，并且支持用户自定义扩展。

当用户传入一个hive sql后，如果有语法错误会返回错误信息，如果没有，首先转化成抽象语法树，然后遍历语法树，提取出每个子查询的where、on、having、limit、groupby、orderby、distict等关键字，表名、字段等所有sql信息， 用户可以继承一个接口，实现接口的方法。在遍历完并提取每个子查询的所有信息后，会自动执行用户实现的方法，也就是说，用户可以对sql语句的每个子语句进行自定义扩展，比如判断每个子查询的表名或字段名是否存在、分区格式是否准确等。

最后把sql封装成一个以子查询为粒度的多叉树的数据结构QueryResult，

##使用方法

    public static void main(String[] args){
        QueryResult queryResult = null;
        try {
            HiveSqlParse hiveSqlParse = new HiveSqlParse();
            //转换成QueryResult对象
            QueryResult qr = hiveSqlParse.parseSql(sql);
            System.out.println(queryResult);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (parseRunningException e) {
            e.printStackTrace();
        }

ParseException是语法错误异常，parseRunningException是解析错误异常。
返回的QueryResult对象是一个树结构，包括sql所有信息、如select字段，where条件等，child结点就是子查询的信息。
推荐大家使用debug测试一下，看看QueryResult的结构

##实现方法
举个例子 输入sql语句：


    select 
        id,
        name,
        company 
    from 
    ( 
        select 
            id,
            name 
        from 
            table1 
    )t1 
    join 
    ( 
        select 
            id,
            company 
        from 
            table2 
    )t2 
    on t1.id = t2.id


1.首先利用hive源码中的转化抽象语法树方式，使用antlr4把sql转化成语法树ASTNode，并使用hive源码的ParseException输出错误信息。

2.然后采用后续遍历语法树，针对每个树的节点，采用工厂模式，创建token工厂，根据不同的token采用不同的策略提取sql的信息，如select字段、where条件、group by、join子查询等。最后把信息保存到QueryResult。

3.在遍历完每个子查询，提取到这个子查询所有的信息到QueryResult后，用户可以自定义加入扩展点。

这里采用了Spring IOC的扩展点PostProcessor的思想，用户可以继承QueryResultProcessor接口，实现eachQueryResultProcess和headResultProcess方法（先执行，只能对头部sql类型、如select、insert扩展做判断），并提供扩展类包的路径。 之后利用反射的方式实例化这些QueryResultProcessor接口的实现类。

接着在遍历完每个子查询后执行用户实现的eachQueryResultProcess方法。 如果需要保存数据可以保存到QueryResult的selfValue字段（hashmap）。做到用户可以自定义扩展的功能。

4.比如说用户可以在扩展类里把每个子句的表名保存到selfValue字段，到最后父节点的QueryResult不仅会包含sql的所有信息，还会保存所有的表名。 还可以在扩展类判断表名或字段名是否存在，如果不存在就抛异常，这样就可以校验一些sql非语法的错误。 当然还有很多场景，这个组件的通用性很高。
